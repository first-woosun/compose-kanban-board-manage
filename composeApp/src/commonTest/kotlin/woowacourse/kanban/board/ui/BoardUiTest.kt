package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import woowacourse.kanban.board.BoardState
import kotlin.test.Test
import woowacourse.kanban.board.components.KanbanBoard
import woowacourse.kanban.board.constant.MockData
import woowacourse.kanban.board.constant.SnackBarText
import woowacourse.kanban.domain.project.KanbanProject
import woowacourse.kanban.domain.task.Assignee
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.Tags
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.TaskStatus
import woowacourse.kanban.domain.task.Title
import java.util.UUID

@OptIn(ExperimentalTestApi::class)
class BoardUiTest {

    val todoTaskProject = KanbanProject(
        title = "Compose1",
        inputTasks = mutableListOf(
            KanbanTask(
                data = TaskData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee.DINO,
                    id = UUID.randomUUID(),
                ),
                status = TaskStatus.TO_DO,
            ),
        )
    )

    val inProgressTaskProject = KanbanProject(
        title = "Compose1",
        inputTasks = mutableListOf(
            KanbanTask(
                data = TaskData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee.DINO,
                    id = UUID.randomUUID(),
                ),
                status = TaskStatus.IN_PROGRESS,
            ),
        )
    )

    val unDeletableTaskProject = KanbanProject(
        title = "Compose1",
        inputTasks = mutableListOf(
            KanbanTask(
                data = TaskData(
                    title = Title("REVIEW"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee.DINO,
                    id = UUID.randomUUID(),
                ),
                status = TaskStatus.REVIEW,
            ),
            KanbanTask(
                data = TaskData(
                    title = Title("DONE"),
                    content = "내용",
                    tags = Tags(),
                    assignee = Assignee.DINO,
                    id = UUID.randomUUID(),
                ),
                status = TaskStatus.DONE,
            ),
        )
    )

    @Test
    fun `새 태스크 생성 버튼을 누르면 생성 다이얼로그가 열려야 한다`() = runComposeUiTest {
        // given : 새 태스크 버튼이 주어진다
        setContent {
            KanbanBoard(
                project = KanbanProject(listOf()),
            )
        }

        // when : 새 태스크 버튼을 눌렀을 때
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        // then : 생성 다이얼로그가 열려야 한다
        onNodeWithText("태스크 제목을 입력하세요").assertExists()
    }

    @Test
    fun `생성 다이얼로그에서 정상적인 값들을 입력 후 생성 버튼을 누르면 칸반 보드 리스트에 표시되어야 한다`() = runComposeUiTest {
        // given : 태스크 카드 정상 입력값이 주어진다
        val project = MockData.MOCK_PROJECTS.first()

        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(project) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = project,
                    boardState = state,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when: 새로운 태스크가 생성됐을 때
        state.addTask { KanbanTask(
            data = TaskData(
                title = Title("title"),
                content = "",
                tags = Tags(emptyList()),
                assignee = Assignee.DINO,
            ),
            status = TaskStatus.TO_DO
        ) }

        // then : 칸반 보드에서 입력된 카드가 보여야 한다
        onNodeWithText("title", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `태스크 카드가 생성되고 스낵바가 출력되어야 한다`() = runComposeUiTest {
        // given : 태스크 카드 정상 입력값이 주어진다
        val project = MockData.MOCK_PROJECTS.first()

        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(project) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = project,
                    boardState = state,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 생성 다이얼로그에서 정상적인 값을 입력 후 생성 버튼을 누를 때
        state.addTask {
            KanbanTask(
                data = TaskData(
                    title = Title("제목"),
                    content = "",
                    tags = Tags(emptyList()),
                    assignee = Assignee.DINO,
                ),
                status = TaskStatus.TO_DO
        )}

        // then : 칸반 보드 하단에 스낵바가 출력되어야 한다
        onNodeWithText(SnackBarText.CREATE_TASK, useUnmergedTree = true).assertExists()
    }

    @Test
    fun `태스크 카드를 클릭하면 TaskEditDialog가 출력되어야 한다`() = runComposeUiTest {
        // given : KanbanBoard가 생성된다
        lateinit var state: BoardState
        lateinit var snackbarHostState: SnackbarHostState

        setContent {
            snackbarHostState = remember { SnackbarHostState() }
            state = remember { BoardState(todoTaskProject) }

            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
                KanbanBoard(
                    project = todoTaskProject,
                    boardState = state,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }

        // when : 태스크 카드를 클릭하면
        onNodeWithText("제목", useUnmergedTree = true).performClick()
        waitForIdle()

        // then : EditTaskDialog가 출력된다.
        onNodeWithText("기존 태스크 수정", useUnmergedTree = true).assertExists()
    }
}

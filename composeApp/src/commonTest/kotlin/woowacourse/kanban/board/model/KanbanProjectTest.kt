package woowacourse.kanban.board.model

import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import woowacourse.kanban.board.BoardState
import woowacourse.kanban.domain.project.KanbanProject
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.Nickname
import woowacourse.kanban.domain.task.Tags
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.TaskStatus
import woowacourse.kanban.domain.task.Title

class KanbanProjectTest {
    @Test
    fun `새 태스크를 생성했을 때 현재 프로젝트에 삽입되어야 한다`() = runTest {
        val project = KanbanProject()

        project.addTask(
            KanbanTask(
                data = TaskData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.DONE,
            ),
        )

        assertEquals(1, project.project.size)
    }

    @Test
    fun `태스크의 상태를 변경 할 수 있어야 한다`() = runTest {
        val project = KanbanProject(
            listOf(KanbanTask(
                    data = TaskData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.IN_PROGRESS,
                )
            )
        )

        project.changeTaskStatus(0, TaskStatus.REVIEW)

        assertEquals(TaskStatus.REVIEW, project.project[0].status)
    }
}

package woowacourse.kanban.board.model

import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test
import woowacourse.kanban.board.BoardState
import woowacourse.kanban.domain.project.IllegalDeleteException
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

    @Test
    fun `TO_DO 상태의 태스크를 삭제 할 수 있다`() = runTest {
        // given: 프로젝트에 TO_DO 상태인 Task가 있을 때
        val project = KanbanProject(
            listOf(KanbanTask(
                data = TaskData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.TO_DO),
            )
        )

        // when: 태스크를 삭제하면
        project.deleteTask(0)

        // 태스크가 삭제된다
        assertThat(project.project.size).isEqualTo(0)
    }

    @Test
    fun `IN_PROGRESS 상태의 태스크를 삭제 할 수 있다`() = runTest {
        // given: 프로젝트에 IN_PROGRESS상태인 Task가 있을 때
        val project = KanbanProject(
            listOf(KanbanTask(
                data = TaskData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.IN_PROGRESS),
            )
        )

        // when: IN_PROGRESS상태인 태스크를 삭제하면
        project.deleteTask(0)

        // 태스크가 삭제된다
        assertThat(project.project.size).isEqualTo(0)
    }

    @Test
    fun `REVIEW 상태의 태스크를 삭제하면 예외가 발생한다`() = runTest {
        // given: 프로젝트에 REVIEW상태인 Task가 있을 때
        val project = KanbanProject(
            listOf(KanbanTask(
                data = TaskData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.REVIEW),
            )
        )

        // when: REVIEW상태인 태스크를 삭제하면
        // then: 예외가 발생한다.
        assertThrows(IllegalDeleteException::class.java) { project.deleteTask(0) }
    }

    @Test
    fun `DONE 상태의 태스크를 삭제하면 예외가 발생한다`() = runTest {
        // given: 프로젝트에 DONE상태인 Task가 있을 때
        val project = KanbanProject(
            listOf(KanbanTask(
                data = TaskData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.DONE),
            )
        )

        // when: DONE상태인 태스크를 삭제하면
        // then: 예외가 발생한다.
        assertThrows(IllegalDeleteException::class.java) { project.deleteTask(0) }
    }
}

package woowacourse.kanban.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.constant.SnackBarText
import woowacourse.kanban.board.utils.SnackBarEvent
import woowacourse.kanban.domain.project.IllegalDeleteException
import woowacourse.kanban.domain.project.KanbanProject
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.TaskData
import woowacourse.kanban.domain.task.TaskStatus
import java.util.UUID

class BoardState(project: KanbanProject) {

    private val totalTasks = mutableStateOf(project)

    private val showDialog = mutableStateOf(false)

    private val isEditTask = mutableStateOf(false)

    fun showDialogValue() = showDialog.value

    fun isEditTaskValue() = isEditTask.value

    fun toggleDialog() {
        showDialog.value = !showDialog.value
    }

    fun toggleEditTask() {
        isEditTask.value = !isEditTask.value
    }

    var snackBarEvent by mutableStateOf<SnackBarEvent?>(null)
        private set

    private fun snackBarTrigger(message: String) {
        snackBarEvent = SnackBarEvent(message = message)
    }

    fun getTaskWithId(targetId: UUID): KanbanTask {
        return totalTasks.value.getTaskWithID(targetId)
    }


    fun addTask(inputTask: KanbanTask) {
        totalTasks.value.addTask(inputTask)
        snackBarTrigger(SnackBarText.CREATE_TASK)
    }

    fun changeTaskStatus(targetIndex: Int, targetStatus: TaskStatus) {
        try {
            totalTasks.value.changeTaskStatus(targetIndex, targetStatus)
            snackBarTrigger(SnackBarText.MOVE_TASK)
        } catch (e: IllegalStateException) {
            snackBarTrigger(SnackBarText.INVALID_MOVE_TASK)
        } catch (e: IllegalArgumentException) {
            snackBarTrigger(SnackBarText.NONE_ASSIGNEE)
        }
    }

    fun deleteTask(targetId: UUID) {
        try {
            totalTasks.value.deleteTask(targetId)
        } catch (e: IllegalDeleteException) {
            snackBarTrigger(SnackBarText.INVALID_DELETE_TASK)
        }

    }

    fun editTask(targetId: UUID, inputTask: KanbanTask) {
        totalTasks.value.editTask(targetId, inputTask)
        snackBarTrigger(SnackBarText.EDIT_TASK)
    }
}

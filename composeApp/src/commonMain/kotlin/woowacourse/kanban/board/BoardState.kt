package woowacourse.kanban.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.constant.SnackBarText
import woowacourse.kanban.board.utils.SnackBarEvent
import woowacourse.kanban.domain.project.KanbanProject
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.TaskStatus

class BoardState(project: KanbanProject) {

    private val totalTasks = mutableStateOf(project)

    private val showDialog = mutableStateOf(false)

    fun showDialogValue() = showDialog.value

    fun toggleDialog() {
        showDialog.value = !showDialog.value
    }

    var snackBarEvent by mutableStateOf<SnackBarEvent?>(null)
        private set

    private fun snackBarTrigger(message: String) {
        snackBarEvent = SnackBarEvent(message = message)
    }

    fun addTask(inputTask: KanbanTask) {
        totalTasks.value.addTask(inputTask)
        snackBarTrigger(SnackBarText.CREATE_TASK)
    }

    fun changeTaskStatus(targetIndex: Int, targetStatus: TaskStatus) {
        totalTasks.value.changeTaskStatus(targetIndex, targetStatus)
        snackBarTrigger(SnackBarText.EDIT_TASK)
    }
}

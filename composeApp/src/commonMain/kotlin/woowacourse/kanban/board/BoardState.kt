package woowacourse.kanban.board

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.constant.SnackBarText
import woowacourse.kanban.board.utils.SnackBarEvent
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.TaskStatus

class BoardState(project: List<KanbanTask>) {

    private val totalTasks = mutableStateListOf<KanbanTask>().apply {
        addAll(project)
    }

    val totalTaskCount: Int get() = totalTasks.size

    val todoCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.TO_DO } }

    val inProgressCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.IN_PROGRESS } }

    val doneCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.DONE } }

    val progress by derivedStateOf {
        if (totalTasks.isEmpty()) 0.0 else doneCardList.size.toDouble() / totalTasks.size.toDouble()
    }

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
        totalTasks.add(inputTask)
        snackBarTrigger(SnackBarText.CREATE_TASK)
    }

    fun changeTask(newStatus: TaskStatus, index: Int) {
        totalTasks[index] = totalTasks[index].copy(status = newStatus)
        snackBarTrigger(SnackBarText.EDIT_TASK)
    }

    fun totalTasksGetter(): MutableList<KanbanTask> {
        return totalTasks
    }
}

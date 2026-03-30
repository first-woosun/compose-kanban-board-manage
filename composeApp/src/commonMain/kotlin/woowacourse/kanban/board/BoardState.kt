package woowacourse.kanban.board

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.TaskStatus

class BoardState(
    private val scope: CoroutineScope,
    project: List<KanbanTask>,
    private val snackBarHostState: SnackbarHostState = SnackbarHostState(),
) {

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

    fun toggleDialog(){
        showDialog.value = !showDialog.value
    }

    fun addTask(inputTask: KanbanTask) {
        totalTasks.add(inputTask)
    }

    fun totalTasksGetter(): MutableList<KanbanTask> {
        return totalTasks
    }

    fun showKanbanSnackBar(message: String) {
        scope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            snackBarHostState.showSnackbar(message)
        }
    }
}
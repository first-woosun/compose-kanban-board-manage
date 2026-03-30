package woowacourse.kanban.domain.task

class TaskManager(private val tasks: MutableList<KanbanTask>) {

    fun addTask(task: KanbanTask) {
        tasks.add(task)
    }

    fun changeStatus(
        task: KanbanTask,
        status: TaskStatus,
        idx: Int,
    ) {
        tasks[idx] = task.copy(status = status)
    }
}
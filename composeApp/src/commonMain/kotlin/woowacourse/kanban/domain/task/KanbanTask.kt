package woowacourse.kanban.domain.task

data class KanbanTask(val data: TaskData, val status: TaskStatus) {
    private var taskRules: TaskRules = when(status) {
        TaskStatus.TO_DO -> Todo()
        TaskStatus.IN_PROGRESS -> InProgress()
        TaskStatus.REVIEW -> Review()
        TaskStatus.DONE -> Done()
    }

    val isDeletable get() = taskRules.isDeletable


    fun changeStatus(targetStatue: TaskStatus): KanbanTask {
        taskRules = taskRules.moveTo(targetStatue)
        return copy(status = targetStatue)
    }

    fun changeData(data: TaskData): KanbanTask {
        return copy(data = data)
    }
}

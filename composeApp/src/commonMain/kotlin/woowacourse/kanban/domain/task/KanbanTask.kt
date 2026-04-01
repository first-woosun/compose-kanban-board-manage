package woowacourse.kanban.domain.task

data class KanbanTask(val data: TaskData, val status: TaskStatus) {
    fun changeStatus(targetStatue: TaskStatus): KanbanTask {
        return copy(status = targetStatue)
    }
}

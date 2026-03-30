package woowacourse.kanban.domain.project

import woowacourse.kanban.domain.task.KanbanTask

class KanbanProject(inputTasks: List<KanbanTask> = emptyList(), val title: String = "") {
    private val tasks = mutableListOf<KanbanTask>()
    val project get() = tasks.toList()

    init {
        tasks.addAll(inputTasks)
    }
}

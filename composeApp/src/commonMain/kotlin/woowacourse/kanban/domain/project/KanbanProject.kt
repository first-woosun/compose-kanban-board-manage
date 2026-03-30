package woowacourse.kanban.domain.project

import androidx.compose.runtime.mutableStateListOf
import woowacourse.kanban.domain.task.KanbanTask

class KanbanProject(inputTasks: MutableList<KanbanTask>, val title: String = "") {
    val tasks = mutableStateListOf<KanbanTask>()

    init {
        tasks.addAll(inputTasks)
    }
}

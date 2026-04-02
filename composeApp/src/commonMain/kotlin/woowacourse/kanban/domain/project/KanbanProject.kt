package woowacourse.kanban.domain.project

import java.util.UUID
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.TaskStatus

class KanbanProject(inputTasks: List<KanbanTask> = emptyList(), val title: String = "") {
    private val _project = mutableListOf<KanbanTask>()
    val project get() = _project.toList()

    init {
        _project.addAll(inputTasks)
    }

    fun getTasksWithStatus(targetStatus: TaskStatus): List<KanbanTask> = project.filter { it.status == targetStatus }

    fun getTaskIndexWithId(targetId: UUID) = project.indexOfFirst { it.data.id == targetId }

    fun getProgress(): Double {
        return if (project.isEmpty()) 0.0 else getTasksWithStatus(TaskStatus.DONE).size.toDouble() / project.size.toDouble()
    }

    fun addTask(inputTask: KanbanTask) {
        _project.add(inputTask)
    }

    fun changeTaskStatus(targetIndex: Int, targetStatus: TaskStatus) {
        _project[targetIndex] = _project[targetIndex].changeStatus(targetStatus)
    }
}

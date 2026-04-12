package woowacourse.kanban.domain.project

import java.util.UUID
import woowacourse.kanban.domain.task.KanbanTask
import woowacourse.kanban.domain.task.TaskStatus

data class KanbanProject(private val tasks: List<KanbanTask> = emptyList(), val title: String = "") {

    val projectTasks: List<KanbanTask> get() = tasks

    fun getTasksWithStatus(targetStatus: TaskStatus): List<KanbanTask> = tasks.filter { it.status == targetStatus }

    fun getTaskIndexWithId(targetId: UUID) = tasks.indexOfFirst { it.data.id == targetId }

    fun getProgress(): Double {
        return if (tasks.isEmpty()) 0.0 else getTasksWithStatus(TaskStatus.DONE).size.toDouble() / tasks.size.toDouble()
    }

    fun getTaskWithID(targetId: UUID) = tasks.first { it.data.id == targetId }

    fun addTask(inputTask: KanbanTask): KanbanProject {
        return copy(tasks = tasks + inputTask)
    }

    fun changeTaskStatus(targetIndex: Int, targetStatus: TaskStatus): KanbanProject {
        val newTasks = tasks.toMutableList()
        newTasks[targetIndex] = newTasks[targetIndex].changeStatus(targetStatus)
        return copy(tasks = newTasks)
    }

    fun editTask(targetId: UUID, inputTask: KanbanTask): KanbanProject {
        val index = getTaskIndexWithId(targetId)
        if (index == -1) return this
        val newTasks = tasks.toMutableList()
        newTasks[index] = inputTask
        return copy(tasks = newTasks)
    }

    fun deleteTask(targetId: UUID): KanbanProject {
        val index = getTaskIndexWithId(targetId)
        if (index == -1) return this
        val newTasks = tasks.toMutableList()
        newTasks.removeAt(index)
        return copy(tasks = newTasks)
    }
}

package woowacourse.kanban.domain.task

interface TaskRules {

    fun moveTo(targetStatus: TaskStatus): TaskRules
}

class Todo: TaskRules {

    override fun moveTo(targetStatus: TaskStatus): TaskRules {
        return when(targetStatus) {
            TaskStatus.TO_DO -> this
            TaskStatus.IN_PROGRESS -> InProgress()
            TaskStatus.REVIEW, TaskStatus.DONE -> throw IllegalStateException()
        }
    }
}

class InProgress: TaskRules {

    override fun moveTo(targetStatus: TaskStatus): TaskRules {
        return when(targetStatus) {
            TaskStatus.IN_PROGRESS -> this
            TaskStatus.TO_DO -> Todo()
            TaskStatus.REVIEW -> Review()
            TaskStatus.DONE -> throw IllegalStateException()
        }
    }
}

class Review: TaskRules {

    override fun moveTo(targetStatus: TaskStatus): TaskRules {
        return when(targetStatus) {
            TaskStatus.REVIEW -> this
            TaskStatus.IN_PROGRESS -> InProgress()
            TaskStatus.DONE -> Done()
            TaskStatus.TO_DO -> throw IllegalStateException()
        }
    }
}

class Done: TaskRules {

    override fun moveTo(targetStatus: TaskStatus): TaskRules {
        return when(targetStatus) {
            TaskStatus.DONE -> this
            TaskStatus.TO_DO -> Todo()
            TaskStatus.REVIEW, TaskStatus.IN_PROGRESS -> throw IllegalStateException()
        }
    }
}
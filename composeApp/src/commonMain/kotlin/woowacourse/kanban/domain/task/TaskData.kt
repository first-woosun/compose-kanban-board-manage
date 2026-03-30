package woowacourse.kanban.domain.task

data class TaskData(
    val title: Title,
    val content: String = "",
    val tags: Tags,
    val nickname: Nickname,
    val id: Long = System.currentTimeMillis(),
)

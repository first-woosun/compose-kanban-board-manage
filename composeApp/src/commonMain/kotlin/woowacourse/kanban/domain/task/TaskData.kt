package woowacourse.kanban.domain.task

import java.util.UUID

data class TaskData(
    val title: Title,
    val content: String = "",
    val tags: Tags,
    val nickname: Nickname,
    val id: UUID = UUID.randomUUID()
)

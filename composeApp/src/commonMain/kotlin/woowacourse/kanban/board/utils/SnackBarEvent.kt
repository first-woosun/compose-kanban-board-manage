package woowacourse.kanban.board.utils

import java.util.UUID

class SnackBarEvent(
    val id: String = UUID.randomUUID().toString(),
    val message: String
)
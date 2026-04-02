package woowacourse.kanban.domain.task

import woowacourse.kanban.core.design.ErrorPrefix

@JvmInline
value class Nickname(val nickname: String) {
    init {
        require(nickname.isNotBlank()) { "${ErrorPrefix.ERROR_PREFIX} 닉네임이 비어있으면 안됩니다." }
    }
}

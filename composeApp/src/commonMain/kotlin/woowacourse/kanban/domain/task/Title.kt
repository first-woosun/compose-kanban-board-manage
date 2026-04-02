package woowacourse.kanban.domain.task

import woowacourse.kanban.core.design.ErrorPrefix

@JvmInline
value class Title(val content: String) {
    init {
        require(content.isNotBlank()) { "${ErrorPrefix.ERROR_PREFIX} 제목의 내용이 존재해야 합니다." }
    }

    companion object {
        fun isValidTitle(inputTitle: String) = inputTitle.isEmpty()
    }
}

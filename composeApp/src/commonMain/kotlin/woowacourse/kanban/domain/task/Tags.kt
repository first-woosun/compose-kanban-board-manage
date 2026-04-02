package woowacourse.kanban.domain.task

import woowacourse.kanban.core.design.ErrorPrefix

class Tags(val tags: List<String> = emptyList()) {
    init {
        require(tags.size <= MAX_TAG_SIZE) { "${ErrorPrefix.ERROR_PREFIX}태그는 5개를 초과할 수 없습니다." }
        require(tags.all { it.length <= MAX_TAG_CONTENT_SIZE }) { "${ErrorPrefix.ERROR_PREFIX}태그의 내용은 5자를 초과할 수 없습니다." }
    }

    companion object {
        private const val MAX_TAG_SIZE = 5
        private const val MAX_TAG_CONTENT_SIZE = 5

        fun isValidTags(inputTags: String): Boolean {
            val tags = inputTags.split(",").map { it.trim() }
            return tags.size > 5 || tags.any { it.length > 5 }
        }
    }
}

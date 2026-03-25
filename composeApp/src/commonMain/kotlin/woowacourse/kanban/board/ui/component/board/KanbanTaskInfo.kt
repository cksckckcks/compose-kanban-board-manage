package woowacourse.kanban.board.ui.component.board

import woowacourse.kanban.board.domain.dialog.Status

class KanbanTaskInfo(
    val title: String,
    val status: Status,
    val assignee: String,
    val description: String? = null,
    val tags: List<String> = emptyList(),
)

package woowacourse.kanban.board.domain.result

import woowacourse.kanban.board.domain.EditError
import woowacourse.kanban.board.domain.KanbanBoard

sealed class EditResult {
    data class Success(val board: KanbanBoard) : EditResult()
    data class Failed(val error: EditError) : EditResult()
}

package woowacourse.kanban.board.domain.result

import woowacourse.kanban.board.domain.DeleteError
import woowacourse.kanban.board.domain.KanbanBoard

sealed class DeleteTaskResult {
    data class Success(val board: KanbanBoard) : DeleteTaskResult()
    data class Failed(val error: DeleteError) : DeleteTaskResult()
}

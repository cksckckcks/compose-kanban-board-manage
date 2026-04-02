package woowacourse.kanban.board.exception

import woowacourse.kanban.board.domain.DeleteError

class DeleteException(val error: DeleteError) : Exception()

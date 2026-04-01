package woowacourse.kanban.board.exception

import woowacourse.kanban.board.domain.MoveError

class MoveException(val status: MoveError) : Exception()

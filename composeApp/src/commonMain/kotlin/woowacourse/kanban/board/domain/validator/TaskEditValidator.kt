package woowacourse.kanban.board.domain.validator

import woowacourse.kanban.board.domain.MoveError
import woowacourse.kanban.board.domain.dialog.Status

object TaskEditValidator {
    fun validateEditStatus(
        status: Status,
        newStatus: Status,
        isAssigned: Boolean,
    ): MoveError? = when (status) {
        Status.TO_DO -> {
            when (newStatus) {
                Status.IN_PROGRESS if !isAssigned -> MoveError.UNASSIGNED
                !in listOf(Status.TO_DO, Status.IN_PROGRESS) -> MoveError.INVALID_STATUS
                else -> null
            }
        }

        Status.IN_PROGRESS -> {
            if (newStatus !in listOf(Status.TO_DO, Status.IN_PROGRESS, Status.REVIEW))
                MoveError.INVALID_STATUS
            else
                null
        }

        Status.REVIEW -> {
            if (newStatus !in listOf(Status.IN_PROGRESS, Status.REVIEW, Status.DONE))
                MoveError.INVALID_STATUS
            else
                null
        }

        Status.DONE -> {
            if (newStatus !in listOf(Status.TO_DO, Status.DONE))
                MoveError.INVALID_STATUS
            else
                null
        }
    }
}

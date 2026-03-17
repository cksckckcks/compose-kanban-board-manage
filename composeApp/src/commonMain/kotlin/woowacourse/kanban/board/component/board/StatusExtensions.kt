package woowacourse.kanban.board.component.board

import androidx.compose.ui.graphics.Color
import woowacourse.kanban.board.domain.dialog.Status

fun Status.toKanbanBoardInfo(): KanbanBoardInfo = when (this) {
    Status.TO_DO -> {
        KanbanBoardInfo(
            title = "To Do",
            titleBackgroundColor = Color(0xFF155DFC),
            bodyColor = Color(0xFFEFF6FF),
            borderColor = Color(0xFFBEDBFF),
        )
    }

    Status.IN_PROGRESS -> {
        KanbanBoardInfo(
            title = "In Progress",
            titleBackgroundColor = Color(0xFFE17100),
            bodyColor = Color(0xFFFFFBEB),
            borderColor = Color(0xFFFEE685),
        )
    }

    Status.DONE -> {
        KanbanBoardInfo(
            title = "Done",
            titleBackgroundColor = Color(0xFF00A63E),
            bodyColor = Color(0xFFF0FDF4),
            borderColor = Color(0xFFB9F8CF),
        )
    }
}

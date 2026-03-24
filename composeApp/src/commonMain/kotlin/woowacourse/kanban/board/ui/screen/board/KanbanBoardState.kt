package woowacourse.kanban.board.ui.screen.board

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status

class KanbanBoardState {
    val cards: MutableList<KanbanTask> = mutableStateListOf()
    var isNewTaskDialog by mutableStateOf(false)
        private set
    val snackBarHostState = SnackbarHostState()


    fun addCard(card: KanbanTask) {
        cards.add(card)
    }

    fun showNewTaskDialog() {
        isNewTaskDialog = true
    }

    fun hideNewTaskDialog() {
        isNewTaskDialog = false
    }

    fun getCompleteCount(): Int = cards.count { it.status == Status.DONE }
    fun getTotalCount(): Int = cards.size
}

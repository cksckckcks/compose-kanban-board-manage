package woowacourse.kanban.board.ui.screen.board

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.ui.component.board.KanbanTaskInfo

class KanbanBoardState(
    val kanbanBoard: KanbanBoard,
) {
    var tasks = mutableStateListOf<KanbanTask>()

    var isNewTaskDialog by mutableStateOf(false)
        private set
    val snackBarHostState = SnackbarHostState()

    fun updateTasks() {
        tasks.clear()
        tasks.addAll(kanbanBoard.getTasks())
    }

    fun addTask(kanbanTaskInfo: KanbanTaskInfo) {
        kanbanBoard.createTask(kanbanTaskInfo)
        updateTasks()
    }

    fun showNewTaskDialog() {
        isNewTaskDialog = true
    }

    fun hideNewTaskDialog() {
        isNewTaskDialog = false
    }

    fun getCompleteCount(): Int = tasks.count { it.status == Status.DONE }
    fun getTotalCount(): Int = tasks.size
}

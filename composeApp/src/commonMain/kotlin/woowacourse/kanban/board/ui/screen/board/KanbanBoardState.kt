package woowacourse.kanban.board.ui.screen.board

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status

class KanbanBoardState(
    kanbanBoard: KanbanBoard,
    projects: List<KanbanProject>,
) {
    private val _projects = projects.toList()
    private var _kanbanBoard by mutableStateOf(kanbanBoard)
    private val selectedProjectIds get() = _projects[selectedProjectIndex].getTaskIds()
    var selectedProjectIndex by mutableIntStateOf(0)
        private set
    var isNewTaskDialog by mutableStateOf(false)
        private set
    val snackBarHostState = SnackbarHostState()

    fun updateSelectedProjectIndex(newIndex: Int) {
        selectedProjectIndex = newIndex
    }

    fun getProjectTasksByStatus(status: Status): List<KanbanTask> =
        _kanbanBoard.getTasksByStatus(ids = selectedProjectIds, status = status)

    fun addTask(kanbanTask: KanbanTask) {
        _kanbanBoard = _kanbanBoard.addTask(kanbanTask)
        _projects[selectedProjectIndex].addTaskId(kanbanTask.id)
    }

    fun moveTask(
        task: KanbanTask,
        targetStatus: Status,
    ) {
        _kanbanBoard = _kanbanBoard.changeTaskStatus(task, targetStatus)
    }

    fun showNewTaskDialog() {
        isNewTaskDialog = true
    }

    fun hideNewTaskDialog() {
        isNewTaskDialog = false
    }

    fun getProjectsTitles(): List<String> = _projects.map { it.title }

    fun getCompleteCount(): Int = _kanbanBoard.getCompleteCount(selectedProjectIds)

    fun getTotalCount(): Int = _kanbanBoard.getTotalCount(selectedProjectIds)

    fun getCompleteRatio(): Float = _kanbanBoard.getCompleteRatio(selectedProjectIds)
}

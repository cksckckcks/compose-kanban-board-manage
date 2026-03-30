package woowacourse.kanban.board.ui.screen.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status

class KanbanBoardState(
    kanbanBoard: KanbanBoard,
) {
    private var _kanbanBoard by mutableStateOf(kanbanBoard)
    private val _projects get() = _kanbanBoard.getProjectList()
    private val selectedProject get() = _projects[selectedProjectIndex]
    var selectedProjectIndex by mutableIntStateOf(0)
        private set
    var isNewTaskDialog by mutableStateOf(false)
        private set

    fun updateSelectedProjectIndex(newIndex: Int) {
        selectedProjectIndex = newIndex
    }

    fun getProjectTasksByStatus(status: Status): List<KanbanTask> =
        selectedProject.getTasksByStatus(status = status)

    fun addTask(kanbanTask: KanbanTask) {
        _kanbanBoard = _kanbanBoard
            .addTask(
                projectIndex = selectedProjectIndex,
                task = kanbanTask,
            )
    }

    fun moveTask(
        task: KanbanTask,
        targetStatus: Status,
    ) {
        _kanbanBoard = _kanbanBoard
            .changeTaskStatus(
                projectIndex = selectedProjectIndex,
                task = task,
                newStatus = targetStatus,
            )
    }

    fun showNewTaskDialog() {
        isNewTaskDialog = true
    }

    fun hideNewTaskDialog() {
        isNewTaskDialog = false
    }

    fun getProjectsTitles(): List<String> = _kanbanBoard.getProjectTitles()

    fun getCompleteCount(): Int = selectedProject.getCompleteCount()

    fun getTotalCount(): Int = selectedProject.getTotalCount()

    fun getCompleteRatio(): Float = selectedProject.getCompleteRatio()
}

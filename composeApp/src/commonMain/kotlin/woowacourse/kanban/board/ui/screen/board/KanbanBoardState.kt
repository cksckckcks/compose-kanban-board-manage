package woowacourse.kanban.board.ui.screen.board

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
    private val _projects = projects
    private val _kanbanBoard = kanbanBoard
    private val tasks = mutableStateListOf<KanbanTask>()
    var selectedProjectIndex by mutableIntStateOf(0)
        private set
    var isNewTaskDialog by mutableStateOf(false)
        private set
    val snackBarHostState = SnackbarHostState()

    fun getTasks(): List<KanbanTask> = tasks

    fun updateSelectedProjectIndex(newIndex: Int) {
        selectedProjectIndex = newIndex
        updateTasks()
    }

    fun getTasksByIds(): List<KanbanTask> {
        return _kanbanBoard.getTasks(_projects[selectedProjectIndex].getTaskIds())
    }

    fun updateTasks() {
        tasks.clear()
        tasks.addAll(getTasksByIds())
    }

    fun addTask(kanbanTask: KanbanTask) {
        _kanbanBoard.addTask(kanbanTask)
        _projects[selectedProjectIndex].addTaskId(kanbanTask.id)
        updateTasks()
    }

    fun moveTask(
        task: KanbanTask,
        targetStatus: Status,
    ) {
        _kanbanBoard.changeTaskStatus(task, targetStatus)
        updateTasks()
    }

    fun showNewTaskDialog() {
        isNewTaskDialog = true
    }

    fun hideNewTaskDialog() {
        isNewTaskDialog = false
    }

    fun getProjectsTitles(): List<String> = _projects.map { it.title }

    fun getCompleteCount(): Int = tasks.count { it.status == Status.DONE }

    fun getTotalCount(): Int = tasks.size
}

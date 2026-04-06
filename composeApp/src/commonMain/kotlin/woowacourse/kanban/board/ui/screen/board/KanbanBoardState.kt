package woowacourse.kanban.board.ui.screen.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.domain.result.DeleteTaskResult
import woowacourse.kanban.board.domain.result.EditResult

class KanbanBoardState(kanbanBoard: KanbanBoard) {
    private var _kanbanBoard by mutableStateOf(kanbanBoard)
    private val projects get() = _kanbanBoard.getProjectList()
    private val selectedProject get() = projects[selectedProjectIndex]
    var selectedProjectIndex by mutableIntStateOf(0)
        private set
    var isNewTaskDialog by mutableStateOf(false)
        private set
    var isEditTaskDialog by mutableStateOf(false)
        private set

    var editTargetTask by mutableStateOf<KanbanTask?>(null)
        private set

    fun updateSelectedProjectIndex(newIndex: Int) {
        selectedProjectIndex = newIndex
    }

    fun getProjectTasksByStatus(status: Status): List<KanbanTask> = selectedProject.getTasksByStatus(status = status)

    fun addTask(kanbanTask: KanbanTask) {
        _kanbanBoard = _kanbanBoard.addTask(
            projectIndex = selectedProjectIndex,
            task = kanbanTask,
        )
    }

    fun deleteTask(task: KanbanTask): DeleteTaskResult {
        val result = _kanbanBoard.deleteTask(
            projectIndex = selectedProjectIndex,
            task = task,
        )

        return when (result) {
            is DeleteTaskResult.Success -> {
                _kanbanBoard = result.board

                result
            }
            is DeleteTaskResult.Failed -> {
                result
            }
        }
    }

    fun editTask(task: KanbanTask): EditResult {
        val result = _kanbanBoard.editTask(
            projectIndex = selectedProjectIndex,
            task = task,
        )

        println(result)

        return when (result) {
            is EditResult.Success -> {
                _kanbanBoard = result.board

                result
            }
            is EditResult.Failed -> {
                result
            }
        }
    }

    fun moveTask(
        taskId: Long,
        targetStatus: Status,
    ): EditResult {
        val task = selectedProject.getTaskById(taskId)
        val result = _kanbanBoard.changeTaskStatus(
            projectIndex = selectedProjectIndex,
            task = task,
            newStatus = targetStatus,
        )

        return when (result) {
            is EditResult.Success -> {
                _kanbanBoard = result.board

                result
            }
            is EditResult.Failed -> {
                result
            }
        }
    }

    fun showNewTaskDialog() {
        isNewTaskDialog = true
    }

    fun hideNewTaskDialog() {
        isNewTaskDialog = false
    }

    fun showEditTaskDialog() {
        isEditTaskDialog = true
    }

    fun hideEditTaskDialog() {
        isEditTaskDialog = false
    }

    fun updateEditTargetTask(newTask: KanbanTask) {
        editTargetTask = newTask
    }

    fun getProjectsTitles(): List<String> = _kanbanBoard.getProjectTitles()

    fun getCompleteCount(): Int = selectedProject.getCompleteCount()

    fun getTotalCount(): Int = selectedProject.getTotalCount()

    fun getCompleteRatio(): Float = selectedProject.getCompleteRatio()
}

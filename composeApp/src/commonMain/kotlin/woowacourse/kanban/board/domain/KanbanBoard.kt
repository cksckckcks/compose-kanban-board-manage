package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.domain.validator.TaskDeleteValidator
import woowacourse.kanban.board.domain.validator.TaskEditValidator
import woowacourse.kanban.board.exception.DeleteException
import woowacourse.kanban.board.exception.MoveException

data class KanbanBoard(private val projects: List<KanbanProject> = listOf(KanbanProject("기본 프로젝트"))) {
    private val _projects = projects.toList()

    fun getProjectTitles(): List<String> = _projects.map { it.title }

    fun getProjectList() = _projects

    fun getProject(index: Int) = _projects[index]

    fun changeTaskStatus(
        projectIndex: Int,
        task: KanbanTask,
        newStatus: Status,
    ): KanbanBoard {
        val moveStatus = TaskEditValidator.validateEditStatus(
            status = task.status,
            newStatus = newStatus,
            isAssigned = task.assignee != null,
        )

        if (moveStatus != null)
            throw MoveException(moveStatus)

        val newProject = _projects[projectIndex].changeTaskStatus(task = task, newStatus = newStatus)

        return copy(projects = copyProjects(projectIndex = projectIndex, newProject = newProject))
    }

    fun addTask(
        projectIndex: Int,
        task: KanbanTask,
    ): KanbanBoard {
        val newProject = _projects[projectIndex].addTask(task = task)

        return copy(projects = copyProjects(projectIndex = projectIndex, newProject = newProject))
    }

    fun deleteTask(
        projectIndex: Int,
        task: KanbanTask,
    ): KanbanBoard {
        val deleteError = TaskDeleteValidator.validateDelete(task.status)
        if (deleteError != null)
            throw DeleteException(deleteError)

        val newProject = _projects[projectIndex].deleteTask(taskId = task.id)

        return copy(projects = copyProjects(projectIndex = projectIndex, newProject = newProject))
    }

    fun editTask(
        projectIndex: Int,
        task: KanbanTask,
    ): KanbanBoard {
        val originalStatus = _projects[projectIndex].getTaskById(taskId = task.id).status

        val editError = TaskEditValidator.validateEditStatus(
            status = originalStatus,
            newStatus = task.status,
            isAssigned = task.assignee != null,
        )

        if (editError != null) {
            throw MoveException(editError)
        }

        val newProject = _projects[projectIndex].editTask(task = task)

        return copy(projects = copyProjects(projectIndex = projectIndex, newProject = newProject))
    }

    private fun copyProjects(
        projectIndex: Int,
        newProject: KanbanProject,
    ): List<KanbanProject> {
        return _projects.mapIndexed { index, project -> if (index == projectIndex) newProject else project }
    }
}

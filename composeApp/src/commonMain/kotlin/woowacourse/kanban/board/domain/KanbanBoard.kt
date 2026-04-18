package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.domain.result.DeleteTaskResult
import woowacourse.kanban.board.domain.result.BoardResult
import woowacourse.kanban.board.domain.result.ProjectResult
import woowacourse.kanban.board.domain.validator.TaskDeleteValidator
import woowacourse.kanban.board.domain.validator.TaskEditValidator

data class KanbanBoard(private val projects: List<KanbanProject> = listOf(KanbanProject("기본 프로젝트"))) {
    private val _projects = projects.map { it.copy() }

    fun getProjectTitles(): List<String> = _projects.map { it.title }

    fun getProjectList() = _projects

    fun getProject(index: Int) = _projects[index]

    fun changeTaskStatus(
        projectIndex: Int,
        task: KanbanTask,
        newStatus: Status,
    ): BoardResult<EditError> {
        return when (val result = _projects[projectIndex].changeTaskStatus(task = task, newStatus = newStatus)) {
            is ProjectResult.Success -> {
                BoardResult.Success(copy(projects = copyProjects(projectIndex = projectIndex, newProject = result.project)))
            }
            is ProjectResult.Failed -> {
                BoardResult.Failed(result.error)
            }
        }
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
    ): DeleteTaskResult {
        val deleteError = TaskDeleteValidator.validateDelete(task.status)

        if (deleteError != null)
            return DeleteTaskResult.Failed(deleteError)

        val newProject = _projects[projectIndex].deleteTask(taskId = task.id)

        return DeleteTaskResult.Success(copy(projects = copyProjects(projectIndex = projectIndex, newProject = newProject)))
    }

    fun editTask(
        projectIndex: Int,
        task: KanbanTask,
    ): BoardResult<EditError> {
        val originalStatus = _projects[projectIndex].getTaskById(taskId = task.id).status

        val editError = TaskEditValidator.validateEditStatus(
            status = originalStatus,
            newStatus = task.status,
            isAssigned = task.assignee != null,
        )

        if (editError != null) {
            return BoardResult.Failed(editError)
        }

        val newProject = _projects[projectIndex].editTask(task = task)

        return BoardResult.Success(copy(projects = copyProjects(projectIndex = projectIndex, newProject = newProject)))
    }

    private fun copyProjects(
        projectIndex: Int,
        newProject: KanbanProject,
    ): List<KanbanProject> {
        return _projects.mapIndexed { index, project -> if (index == projectIndex) newProject else project }
    }
}

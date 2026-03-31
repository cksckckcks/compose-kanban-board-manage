package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status

data class KanbanBoard(val projects: List<KanbanProject> = listOf(KanbanProject("기본 프로젝트"))) {
    private val _projects = projects.toList()

    fun getProjectTitles(): List<String> = _projects.map { it.title }

    fun getProjectList() = _projects

    fun getProject(index: Int) = _projects[index]

    fun changeTaskStatus(
        projectIndex: Int,
        task: KanbanTask,
        newStatus: Status,
    ): KanbanBoard {
        val newProject = _projects[projectIndex].changeTaskStatus(task = task, newStatus = newStatus)

        return copy(projects = _projects.mapIndexed { index, project -> if (index == projectIndex) newProject else project })
    }

    fun addTask(
        projectIndex: Int,
        task: KanbanTask,
    ): KanbanBoard {
        val newProject = _projects[projectIndex].addTask(task = task)

        return copy(projects = _projects.mapIndexed { index, project -> if (index == projectIndex) newProject else project })
    }
}

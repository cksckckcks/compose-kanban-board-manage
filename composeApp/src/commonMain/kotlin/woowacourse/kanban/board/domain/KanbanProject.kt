package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status

data class KanbanProject(
    val title: String,
    private val tasks: List<KanbanTask> = emptyList(),
) {
    private val _tasks = tasks.toList()

    fun getTasks(): List<KanbanTask> = _tasks

    fun addTask(task: KanbanTask): KanbanProject {
        return copy(tasks = _tasks + task)
    }

    fun changeTaskStatus(
        task: KanbanTask,
        newStatus: Status,
    ): KanbanProject {
        val newTask = task.changeStatus(newStatus = newStatus)

        return copy(tasks = _tasks - task + newTask)
    }

    fun getTasksByStatus(status: Status): List<KanbanTask> = _tasks.filter { it.status == status }

    fun getCompleteCount() = _tasks.count { it.status == Status.DONE }

    fun getTotalCount() = _tasks.size

    fun getCompleteRatio(): Float {
        val total = getTotalCount()
        return if (total == 0) 0f else getCompleteCount().toFloat() / total
    }
}

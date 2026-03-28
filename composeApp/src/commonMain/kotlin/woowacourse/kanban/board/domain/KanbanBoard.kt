package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status

data class KanbanBoard(
    val tasks: List<KanbanTask> = emptyList(),
) {
    private val _tasks = tasks.toList()

    fun getTasks(ids: List<Long>): List<KanbanTask> = _tasks.filter { ids.contains(it.id) }

    fun getTasksByStatus(ids: List<Long>, status: Status) =
        _tasks.filter { ids.contains(it.id) && it.status == status }

    fun addTask(task: KanbanTask): KanbanBoard {
        return copy(tasks = tasks + task)
    }

    fun changeTaskStatus(
        task: KanbanTask,
        newStatus: Status,
    ): KanbanBoard {
        val newTask = task.changeStatus(newStatus = newStatus)

        return copy(tasks = tasks - task + newTask)
    }

    fun getCompleteCount(ids: List<Long>) = tasks.count { ids.contains(it.id) && it.status == Status.DONE }

    fun getTotalCount(ids: List<Long>) = tasks.count { ids.contains(it.id) }

    fun getCompleteRatio(ids: List<Long>): Float {
        val total = getTotalCount(ids)
        return if (total == 0) 0f else getCompleteCount(ids).toFloat() / total
    }
}

package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status

class KanbanBoard(tasks: List<KanbanTask> = emptyList()) {
    private val _tasks: MutableList<KanbanTask> = tasks.toMutableList()

    fun getTasks(kanbanIds: List<Long>): List<KanbanTask> = _tasks.filter { kanbanIds.contains(it.id) }

    fun addTask(task: KanbanTask) {
        _tasks.add(task)
    }

    fun changeTaskStatus(
        task: KanbanTask,
        newStatus: Status,
    ) {
        val newTask = task.changeStatus(newStatus = newStatus)

        _tasks.remove(task)
        _tasks.add(newTask)
    }
}

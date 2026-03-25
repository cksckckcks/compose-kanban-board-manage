package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status

class KanbanBoard(
    private val tasks: MutableList<KanbanTask> = mutableListOf(),
) {
    fun getTasks(): List<KanbanTask> = tasks

    fun createTask(task: KanbanTask) {
        tasks.add(task)
    }

    fun changeTaskStatus(task: KanbanTask, newStatus: Status) {
        val newTask = task.changeStatus(newStatus = newStatus)

        tasks.remove(task)
        tasks.add(newTask)
    }
}

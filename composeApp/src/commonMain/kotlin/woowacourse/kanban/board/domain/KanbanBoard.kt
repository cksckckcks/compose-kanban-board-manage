package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status

class KanbanBoard(
    private val tasks: MutableList<KanbanTask> = mutableListOf(),
) {
    fun getTasks(): List<KanbanTask> = tasks

    fun createTask(task: KanbanTask) {
        tasks.add(task)
    }

    fun changeTaskStatus(task: KanbanTask, targetStatus: Status) {
        val index = tasks.indexOfFirst { it.id == task.id }

        if (index != -1) {
            tasks[index] = tasks[index].copy(
                status = targetStatus,
            )
        }

    }
}

package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.ui.component.board.KanbanTaskInfo

class KanbanBoard(val title: String) {
    private var kanbanTaskId = 0L
    private val _tasks = mutableListOf<KanbanTask>()
    fun getTasks(): List<KanbanTask> = _tasks

    fun createTask(taskInfo: KanbanTaskInfo) {
        val task = KanbanTask(
            id = kanbanTaskId++,
            title = taskInfo.title,
            status = taskInfo.status,
            assignee = taskInfo.assignee,
            description = taskInfo.description,
            tags = taskInfo.tags,
        )

        _tasks.add(task)
    }

    fun changeTaskStatus(task: KanbanTask, targetStatus: Status) {
        val newTask = task.copy(
            status = targetStatus,
        )

        _tasks.remove(task)
        _tasks.add(newTask)
    }
}

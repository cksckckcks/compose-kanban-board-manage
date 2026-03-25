package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.ui.component.board.KanbanTaskInfo

class KanbanBoard(
    val title: String,
    private val tasks: MutableList<KanbanTask> = mutableListOf<KanbanTask>(),
) {
    private var kanbanTaskId = 0L

    fun getTasks(): List<KanbanTask> = tasks

    fun createTask(taskInfo: KanbanTaskInfo) {
        val task = KanbanTask(
            id = kanbanTaskId++,
            title = taskInfo.title,
            status = taskInfo.status,
            assignee = taskInfo.assignee,
            description = taskInfo.description,
            tags = taskInfo.tags,
        )

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

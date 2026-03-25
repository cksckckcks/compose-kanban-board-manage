package woowacourse.kanban.board.domain

class KanbanProject(
    val title: String,
) {
    private val taskIds = mutableListOf<Long>()

    fun getTaskIds(): List<Long> = taskIds

    fun addTaskId(taskId: Long) {
        taskIds.add(taskId)
    }
}

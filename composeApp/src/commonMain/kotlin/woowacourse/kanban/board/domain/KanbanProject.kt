package woowacourse.kanban.board.domain

class KanbanProject(
    val title: String,
    taskIds: List<Long> = emptyList(),
) {
    private val _taskIds: MutableList<Long> = taskIds.toMutableList()

    fun getTaskIds(): List<Long> = _taskIds.toList()

    fun addTaskId(taskId: Long) {
        _taskIds.add(taskId)
    }
}

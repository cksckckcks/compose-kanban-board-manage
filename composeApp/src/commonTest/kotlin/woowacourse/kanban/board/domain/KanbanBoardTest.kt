package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.dialog.Status

class KanbanBoardTest {
    @Test
    fun `칸반 보드 태스크를 생성했을 때 리스트에 추가된다`() {
        // Given
        var kanbanBoard = KanbanBoard()
        val kanbanIds = listOf(4L)

        // When
        val newTask = KanbanTask(
            id = 4L,
            title = "새로운 기능 구현",
            description = "이 기능은 매우 중요합니다.",
            tags = listOf("긴급", "백엔드"),
            status = Status.TO_DO,
            assignee = "별터",
        )
        kanbanBoard = kanbanBoard.addTask(
            task = newTask,
        )

        // Then
        assertThat(kanbanBoard.getTasksByStatus(ids = kanbanIds, status = Status.TO_DO).count { it.assignee == "별터" }).isEqualTo(1)
    }

    @Test
    fun `칸반 보드 태스크의 상태를 수정했을 때 상태가 반영된다`() {
        // Given
        var kanbanBoard = KanbanBoard(tasks = tasks)
        val kanbanIds = listOf(0L, 1L, 2L)

        // When
        kanbanBoard = kanbanBoard.changeTaskStatus(tasks.first(), Status.IN_PROGRESS)

        // Then
        assertThat(kanbanBoard.getTasksByStatus(ids = kanbanIds, status = Status.IN_PROGRESS).size).isEqualTo(1)
    }

    val tasks = mutableListOf(
        KanbanTask(
            id = 0L,
            title = "LazyColumn 컴포넌트 구현",
            description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            tags = listOf("컴포넌트", "성능"),
            status = Status.TO_DO,
            assignee = "다이노",
        ),
        KanbanTask(
            id = 1L,
            title = "안녕하세요",
            description = "내용 내용 내용 내용 내용 내용 내용 내용",
            tags = listOf("안녕", "하세요"),
            status = Status.TO_DO,
            assignee = "다이노",
        ),
        KanbanTask(
            id = 2L,
            title = "페어프로그래밍",
            description = "1단계 미션을 수행합니다",
            tags = listOf("페어", "코딩"),
            status = Status.TO_DO,
            assignee = "볼트",
        ),
    )

    @Test
    fun `완료된 태스크 개수를 정상적으로 반환한다`() {
        // Given
        val kanbanIds = listOf(0L, 1L, 2L)
        var kanbanBoard = KanbanBoard(tasks = tasks)

        // When
        kanbanBoard = kanbanBoard
            .changeTaskStatus(tasks[0], Status.DONE)
            .changeTaskStatus(tasks[1], Status.DONE)

        // Then
        assertThat(kanbanBoard.getCompleteCount(kanbanIds)).isEqualTo(2)
    }

    @Test
    fun `태스크 개수를 정상적으로 반환한다`() {
        // Given
        val kanbanIds = listOf(0L, 1L, 2L)
        val kanbanBoard = KanbanBoard(tasks = tasks)

        // Then
        assertThat(kanbanBoard.getTotalCount(kanbanIds)).isEqualTo(3)
    }

    @Test
    fun `태스크가 없을 때 완료율은 0이다`() {
        // Given
        val kanbanBoard = KanbanBoard()

        // Then
        assertThat(kanbanBoard.getCompleteRatio(emptyList())).isEqualTo(0f)
    }

    @Test
    fun `완료율이 정상적으로 계산된다`() {
        // Given
        val kanbanIds = listOf(0L, 1L, 2L)
        var kanbanBoard = KanbanBoard(tasks = tasks)

        // When
        kanbanBoard = kanbanBoard.changeTaskStatus(tasks[0], Status.DONE)

        // Then
        assertThat(kanbanBoard.getCompleteRatio(kanbanIds)).isEqualTo(1f / 3f)
    }
}

package woowacourse.kanban.board.domain

import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.dialog.Status
import kotlin.test.Test

class KanbanBoardTest {
    val tasks = mutableListOf(
        KanbanTask(
            title = "LazyColumn 컴포넌트 구현",
            description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            tags = listOf("컴포넌트", "성능"),
            status = Status.TO_DO,
            assignee = "다이노",
        ),
        KanbanTask(
            title = "안녕하세요",
            description = "내용 내용 내용 내용 내용 내용 내용 내용",
            tags = listOf("안녕", "하세요"),
            status = Status.TO_DO,
            assignee = "다이노",
        ),
        KanbanTask(
            title = "페어프로그래밍",
            description = "1단계 미션을 수행합니다",
            tags = listOf("페어", "코딩"),
            status = Status.TO_DO,
            assignee = "볼트",
        ),
    )

    @Test
    fun `칸반 보드 내부 태스크 리스트가 정상적으로 호출된다`() {
        // Given & When
        val kanbanBoard = KanbanBoard(
            tasks = tasks,
        )

        // Then
        assertThat(kanbanBoard.getTasks()).isEqualTo(tasks)
    }

    @Test
    fun `칸반 보드 태스크를 생성했을 때 리스트에 추가된다`() {
        // Given
        val kanbanBoard = KanbanBoard()

        // When
        val newTask = KanbanTask(
            title = "새로운 기능 구현",
            description = "이 기능은 매우 중요합니다.",
            tags = listOf("긴급", "백엔드"),
            status = Status.TO_DO,
            assignee = "별터",
        )
        kanbanBoard.createTask(
            task = newTask,
        )

        // Then
        assertThat(kanbanBoard.getTasks().count { it.assignee == "별터" }).isEqualTo(1)
    }

    @Test
    fun `칸반 보드 태스크의 상태를 수정했을 때 상태가 반영된다`() {
        // Given
        val kanbanBoard = KanbanBoard(tasks = tasks)

        // When
        kanbanBoard.changeTaskStatus(tasks.first(), Status.IN_PROGRESS)

        // Then
        assertThat(kanbanBoard.getTasks().count { it.status == Status.IN_PROGRESS }).isEqualTo(1)
    }
}
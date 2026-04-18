package woowacourse.kanban.board.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.domain.result.ProjectResult

class KanbanProjectTest {
    @Test
    fun `완료된 태스크 개수를 정상적으로 반환한다`() {
        // Given
        var kanbanProject = KanbanProject(title = "안녕", tasks = tasks)

        // When
        val result = kanbanProject.changeTaskStatus(tasks[0], Status.DONE) as ProjectResult.Success
        kanbanProject = result.project

        // Then
        assertThat(kanbanProject.getCompleteCount()).isEqualTo(1)
    }

    @Test
    fun `태스크 개수를 정상적으로 반환한다`() {
        // Given & When
        val kanbanProject = KanbanProject(title = "안녕", tasks = tasks)

        // Then
        assertThat(kanbanProject.getTotalCount()).isEqualTo(3)
    }

    @Test
    fun `태스크가 없을 때 완료율은 0이다`() {
        // Given & When
        val kanbanProject = KanbanProject(title = "안녕")

        // Then
        assertThat(kanbanProject.getCompleteRatio()).isEqualTo(0f)
    }

    @Test
    fun `완료율이 정상적으로 계산된다`() {
        // Given
        var kanbanProject = KanbanProject(title = "크롱", tasks = tasks)

        // When
        val result = kanbanProject.changeTaskStatus(tasks[0], Status.DONE) as ProjectResult.Success
        kanbanProject = result.project

        // Then
        assertThat(kanbanProject.getCompleteRatio()).isEqualTo(1f / 3f)
    }

    val tasks = mutableListOf(
        KanbanTask(
            id = 0L,
            title = "LazyColumn 컴포넌트 구현",
            description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
            tags = listOf("컴포넌트", "성능"),
            status = Status.REVIEW,
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
}

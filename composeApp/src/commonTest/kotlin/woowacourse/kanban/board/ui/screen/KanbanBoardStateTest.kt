package woowacourse.kanban.board.ui.screen

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.ui.screen.board.KanbanBoardState

class KanbanBoardStateTest {
    @Test
    fun `프로젝트 이동 시 프로젝트에 맞는 태스크만 전달한다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.TO_DO,
        )
        val projects = listOf(
            KanbanProject(
                title = "안녕",
                tasks = listOf(
                    KanbanTask(
                        id = 0L,
                        title = "안녕",
                        assignee = "볼트",
                        status = Status.TO_DO,
                    ),
                    KanbanTask(
                        id = 2L,
                        title = "안녕",
                        assignee = "볼트",
                        status = Status.TO_DO,
                    ),
                ),
            ),
            KanbanProject(
                title = "잘가",
                tasks = listOf(task),
            ),
        )
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.updateSelectedProjectIndex(1)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.TO_DO)).isEqualTo(listOf(task))
    }

    @Test
    fun `태스크가 프로젝트에 정상적으로 추가된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.TO_DO,
        )

        val projects = listOf(
            KanbanProject(
                title = "안녕",
                tasks = listOf(),
            ),
            KanbanProject(
                title = "잘가",
                tasks = listOf(
                    KanbanTask(
                        id = 0L,
                        title = "안녕",
                        assignee = "볼트",
                        status = Status.TO_DO,
                    ),
                    KanbanTask(
                        id = 2L,
                        title = "안녕",
                        assignee = "볼트",
                        status = Status.TO_DO,
                    ),
                ),
            ),
        )

        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.addTask(task)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.TO_DO)).isEqualTo(listOf(task))
    }

    @Test
    fun `태스크의 상태가 정상적으로 변경된다`() {
        // Given
        val task = KanbanTask(
            id = 1L,
            title = "안녕",
            assignee = "볼트",
            status = Status.TO_DO,
        )

        val projects = listOf(KanbanProject(title = "안녕", tasks = listOf(task)))
        val kanbanBoard = KanbanBoard(projects = projects)

        val kanbanBoardState = KanbanBoardState(kanbanBoard = kanbanBoard)

        // When
        kanbanBoardState.moveTask(task = task, targetStatus = Status.DONE)

        // Then
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.DONE).size).isEqualTo(1)
        assertThat(kanbanBoardState.getProjectTasksByStatus(Status.TO_DO).size).isEqualTo(0)
    }

    @Test
    fun `프로젝트 타이틀 리스트를 정상적으로 반환한다`() {
        // Given & When
        val titles = listOf("안녕", "잘가")
        val projects = titles.map { KanbanProject(title = it) }

        val kanbanBoardState = KanbanBoardState(kanbanBoard = KanbanBoard(projects = projects))

        // Then
        assertThat(kanbanBoardState.getProjectsTitles()).isEqualTo(titles)
    }
}

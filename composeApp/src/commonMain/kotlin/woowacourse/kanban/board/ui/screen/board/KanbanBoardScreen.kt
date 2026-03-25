package woowacourse.kanban.board.ui.screen.board

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status
import woowacourse.kanban.board.ui.component.board.CardGroup
import woowacourse.kanban.board.ui.component.board.KanbanBoardTopAppBar
import woowacourse.kanban.board.ui.component.board.KanbanTaskInfo
import woowacourse.kanban.board.ui.component.dialog.TaskDialog

@Composable
fun KanbanBoardScreen(
    kanbanBoardState: KanbanBoardState = remember { KanbanBoardState(KanbanBoard(title = "Compose Desktop 칸반 보드")) },
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = kanbanBoardState.snackBarHostState

    val totalCount = kanbanBoardState.getTotalCount()
    val completeCount = kanbanBoardState.getCompleteCount()
    val progress = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()
    val progressPercent = (progress * 100).toInt()

    KanbanBoardContent(
        cards = kanbanBoardState.tasks,
        completeCount = completeCount,
        totalCount = totalCount,
        progress = progress,
        progressPercent = progressPercent,
        isNewTaskDialog = kanbanBoardState.isNewTaskDialog,
        onNewTaskClick = {
            kanbanBoardState.showNewTaskDialog()
        },
        onDismissClick = {
            kanbanBoardState.hideNewTaskDialog()
        },
        snackHost = snackBarHostState,
        onCreateClick = {
            kanbanBoardState.addTask(it)
            kanbanBoardState.hideNewTaskDialog()

            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = "새로운 태스크가 추가되었습니다.",
                    withDismissAction = true,
                )
            }
        },
        onMoveTask = { task, targetStatus ->
            kanbanBoardState.moveTask(task, targetStatus)

            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = "태스크가 이동되었습니다.",
                    withDismissAction = true,
                    duration = SnackbarDuration.Short,
                )
            }
        },
    )
}

@Composable
private fun KanbanBoardContent(
    cards: List<KanbanTask>,
    completeCount: Int,
    totalCount: Int,
    progress: Float,
    progressPercent: Int,
    isNewTaskDialog: Boolean,
    snackHost: SnackbarHostState,
    onNewTaskClick: () -> Unit,
    onDismissClick: () -> Unit,
    onCreateClick: (KanbanTaskInfo) -> Unit,
    onMoveTask: (KanbanTask, Status) -> Unit,
) {
    // drag
    var draggedTask by remember { mutableStateOf<KanbanTask?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<Status, Rect>() }

    Scaffold(
        topBar = {
            KanbanBoardTopAppBar(
                title = "Compose Desktop 칸반 보드",
                progress = progress,
                progressPercent = progressPercent,
                completeCount = completeCount,
                totalCount = totalCount,
                onNewTaskClick = onNewTaskClick,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackHost) },
        containerColor = Color.White,
    ) { innerPadding ->
        CardGroup(
            cards = cards,
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp),
            getIsDropTarget = { status ->
                currentDragPosition?.let { columnBounds[status]?.contains(it) == true } ?: false
            },
            onBoundsChanged = { rect, status ->
                columnBounds[status] = rect
            },
            onTaskDragStart = { task ->
                draggedTask = task
            },
            onTaskDragChange = { pos ->
                currentDragPosition = pos
            },
            onTaskDragEnd = {
                val dropPosition = currentDragPosition ?: return@CardGroup
                val targetStatus = columnBounds.entries
                    .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                draggedTask?.let { task ->
                    if (task.status != targetStatus) {
                        onMoveTask(task, targetStatus ?: return@let)
                    }
                }
                currentDragPosition = null
                draggedTask = null
            },
            onTaskDragCancel = {
                currentDragPosition = null
                draggedTask = null
            },
        )

        if (isNewTaskDialog) {
            TaskDialog(
                onDismissClick = onDismissClick,
                onCreateClick = onCreateClick,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 1200)
@Composable
private fun KanbanBoardContentPreview() {
    KanbanBoardContent(
        cards = listOf(
            KanbanTask(
                id = 0,
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.TO_DO,
                assignee = "다이노",
            ),
            KanbanTask(
                id = 0,
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.TO_DO,
                assignee = "다이노",
            ),
            KanbanTask(
                id = 0,
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.IN_PROGRESS,
                assignee = "다이노",
            ),
            KanbanTask(
                id = 0,
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.DONE,
                assignee = "다이노",
            ),
            KanbanTask(
                id = 0,
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.DONE,
                assignee = "다이노",
            ),
            KanbanTask(
                id = 0,
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.DONE,
                assignee = "다이노",
            ),
        ),
        completeCount = 3,
        totalCount = 6,
        progress = 0.5f,
        progressPercent = 50,
        isNewTaskDialog = false,
        onNewTaskClick = { },
        onCreateClick = { },
        snackHost = SnackbarHostState(),
        onDismissClick = { },
        onMoveTask = { _, _ -> },
    )
}

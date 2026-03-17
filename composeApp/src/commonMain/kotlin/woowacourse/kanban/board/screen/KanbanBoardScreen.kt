package woowacourse.kanban.board.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import woowacourse.kanban.board.component.board.CardGroup
import woowacourse.kanban.board.component.board.KanbanBoardTopAppBar
import woowacourse.kanban.board.component.dialog.TaskDialog
import woowacourse.kanban.board.domain.KanbanTask
import woowacourse.kanban.board.domain.dialog.Status

@Composable
fun KanbanBoardScreen() {
    val cards: MutableList<KanbanTask> = remember { mutableStateListOf() }
    var isNewTaskDialog by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val completeCount = cards.count { it.status == Status.DONE }
    val totalCount = cards.size
    val progress = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()
    val progressPercent = (progress * 100).toInt()

    KanbanBoardContent(
        cards = cards,
        completeCount = completeCount,
        totalCount = totalCount,
        progress = progress,
        progressPercent = progressPercent,
        isNewTaskDialog = isNewTaskDialog,
        onNewTaskClick = {
            isNewTaskDialog = true
        },
        onDismissClick = {
            isNewTaskDialog = false
        },
        snackHost = snackBarHostState,
        onCreateClick = {
            cards.add(it)
            isNewTaskDialog = false

            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = "새로운 태스크가 추가되었습니다.",
                    withDismissAction = true,
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
    onCreateClick: (KanbanTask) -> Unit,
) {

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
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.TO_DO,
                assignee = "다이노",
            ),
            KanbanTask(
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.TO_DO,
                assignee = "다이노",
            ),
            KanbanTask(
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.IN_PROGRESS,
                assignee = "다이노",
            ),
            KanbanTask(
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.DONE,
                assignee = "다이노",
            ),
            KanbanTask(
                title = "LazyColumn 컴포넌트 구현",
                description = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
                tags = listOf("컴포넌트", "성능"),
                status = Status.DONE,
                assignee = "다이노",
            ),
            KanbanTask(
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
    )
}

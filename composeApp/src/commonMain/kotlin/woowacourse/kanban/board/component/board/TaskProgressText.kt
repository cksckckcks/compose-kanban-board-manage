package woowacourse.kanban.board.component.board

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TaskProgressText(
    progressPercent: Int,
    completeCount: Int,
    totalCount: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "완료율 : $progressPercent% ($completeCount/$totalCount)",
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF6A7282),
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskProgressTextPreview() {
    TaskProgressText(
        progressPercent = 50,
        completeCount = 3,
        totalCount = 6,
    )
}

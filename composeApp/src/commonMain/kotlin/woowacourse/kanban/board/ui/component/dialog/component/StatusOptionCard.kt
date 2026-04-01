package woowacourse.kanban.board.ui.component.dialog.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.domain.dialog.Status

@Composable
fun StatusOptionCard(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {


    TaskOptionCard(
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.Blue else Black,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 14.dp, horizontal = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

private class StatusOptionCardParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(
        true,
        false,
    )
}

@Preview(showBackground = true)
@Composable
private fun StatusOptionCardPreview(@PreviewParameter(StatusOptionCardParameterProvider::class) isSelected: Boolean) {
    StatusOptionCard(
        text = "To Do",
        isSelected = isSelected,
        onClick = { },
    )
}

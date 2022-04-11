package com.example.jetpacktodo.ui.widget


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpacktodo.R


@Composable
fun NovaSwitch(
    onTitle: String,
    offTitle: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    switchPadding: Dp = 2.dp,
    shape: Shape = RoundedCornerShape(50),
    onCheckedChange: (Boolean) -> Unit,
) {
    val toggleText = if (checked) onTitle else offTitle
    BoxWithConstraints(
        modifier = modifier
    ) {
        val startPadding by animateDpAsState(
            if (checked) maxWidth / 6 else switchPadding
        )
        val endPadding by animateDpAsState(
            if (checked) switchPadding else maxWidth / 6
        )
        val backgroundColor = if (checked) colorResource(id = R.color.purple_700) else MaterialTheme.colors.primaryVariant
        val buttonColor = if (checked) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant
        Surface(
            shape = shape,
            color = backgroundColor,
        ) {
            Surface(
                modifier = Modifier
                    .clickable {
                        onCheckedChange(checked.not())
                    }
                    .padding(
                        top = switchPadding, bottom = switchPadding,
                        start = startPadding, end = endPadding
                    ),
                shape = shape,
                color = buttonColor,
            ) {
                Text(
                    text = toggleText,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Preview(backgroundColor = 0xff000000)
@Composable
private fun ToggleButtonPreview() {
    val toggle = remember {
        mutableStateOf(true)
    }
    NovaSwitch(
        onTitle = "ON",
        offTitle = "Off",
        checked = toggle.value,
    ) {
        toggle.value = it
    }
}
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomToggleButton(
    onTitle: String,
    offTitle: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    switchPadding:Dp = 4.dp,
    toggleBgColor: Color = Color.Gray,
    shape: Shape = RoundedCornerShape(50),
    onCheckedValue: (Boolean) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {

        val startPadding by animateDpAsState(
            targetValue =  if(checked) maxWidth / 5 else switchPadding
        )
        val endPadding by animateDpAsState(
            targetValue = if(checked) switchPadding else maxWidth / 5
        )
        val buttonColor = Color.White
        val togglecolor = if(checked) toggleBgColor else Color.Gray

        Surface(
            shape = shape,
            color = togglecolor,
        ) {

            Surface(
                shape = shape,
                color = buttonColor,
                modifier = Modifier.clickable {
                    onCheckedValue(checked.not())
                }
                    .padding(
                    top = switchPadding, bottom = switchPadding,
                    start = startPadding, end = endPadding
                )
            ) {

                Text(
                    text = if (checked) onTitle else offTitle,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp)
                )
            }

        }
    }


}

@Preview
@Composable
fun ToggleButtonPreview1() {
    val switchValue = remember {
        mutableStateOf(false)
    }
    CustomToggleButton(
        onTitle = "ON",
        offTitle = "OFF",
        checked = switchValue.value,
    ) {

    }
}
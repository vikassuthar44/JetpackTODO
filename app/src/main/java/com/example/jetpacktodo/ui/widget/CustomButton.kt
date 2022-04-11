package com.example.jetpacktodo.ui.widget



import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    buttonTitle:String = "Submit",
    buttonTextColor:Color = Color.White,
    shape: Shape = RoundedCornerShape(size = 20.dp),
    elevation:Dp = 5.dp,
    backgroundColor:Color = MaterialTheme.colors.primary,
    onClick: () -> Unit
) {

    val selected = remember {
        mutableStateOf(false)
    }
    val scale = animateFloatAsState(targetValue = if(selected.value) 0.9f else 1f)
   Surface(
       shape = shape,
       elevation = elevation,
       modifier = modifier
           .scale(scale.value)
           .fillMaxWidth(),
       color = backgroundColor
   ) {
       Column(
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center,
           modifier = Modifier
               .clickable {
                   onClick.invoke()
               }
              /* .pointerInteropFilter {
                   when(it.action) {
                       MotionEvent.ACTION_DOWN ->
                       {
                           selected.value = true
                       }
                       MotionEvent.ACTION_UP -> {
                           selected.value = false
                       }
                       else -> {
                           selected.value = false
                       }
                   }
                   true
               }*/

       ) {
           Text(
               text = buttonTitle,
               modifier = Modifier
                   .padding(all = 20.dp)
                   .wrapContentWidth(align = Alignment.CenterHorizontally),
               style = MaterialTheme.typography.button.copy(
                   color = buttonTextColor,
                   fontWeight = FontWeight.ExtraBold,
                   fontSize = 24.sp
               )
           )
       }

   }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview(){
    CustomButton() {

    }
}
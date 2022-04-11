package com.example.jetpacktodo.ui.widget

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.jetpacktodo.ui.theme.newBackground


@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(
        placeHolderTitle = "Please enter name here!",
        initialValue = "",
        modifier = Modifier.fillMaxWidth()
    ) {

    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    placeHolderTitle: String,
    initialValue: String = "",
    shape: Shape = RoundedCornerShape(10),
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    keyboardType:KeyboardType = KeyboardType.Text,
    onValueChange:(String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var fieldValue by remember {
        mutableStateOf(initialValue)
    }
    Surface(
        modifier = modifier,
        shape = shape,
        color = MaterialTheme.colors.newBackground
    ) {
        Row {
            TextField(
                value = fieldValue,
                textStyle = TextStyle.Default.copy(
                    fontSize = fontSize,
                    fontWeight = fontWeight
                ),
                modifier = modifier,
                onValueChange = { newValue ->
                    fieldValue = newValue
                    onValueChange.invoke(newValue)
                },
                placeholder = {
                    Text(
                        text = placeHolderTitle,
                        style = MaterialTheme.typography.button.copy(
                            fontSize = fontSize
                        )
                    )
                },
                maxLines = maxLines,
                singleLine = singleLine,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    disabledLabelColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                    onGo = {
                        focusManager.clearFocus()
                    },
                    onNext = {
                        focusManager.clearFocus()
                    }
                )
            )
        }

    }
}
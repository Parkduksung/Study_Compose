package com.example.maxlengthtextfiled

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.maxlengthtextfiled.ui.theme.StudyComposeTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
                    CustomTextField()
//                }
            }
        }
    }
}


@Composable
fun CustomTextField() {
    val inputValue = remember { mutableStateOf("") }

    val context = LocalContext.current

    OutlinedTextField(
        value = inputValue.value,
        onValueChange = { newValue ->
//            val isBackspace = newValue.text.length < inputValue.value.text.length
//            if (newValue.text.length <= 10 || isBackspace) {
//                inputValue.value = newValue.copy(
//                    selection = TextRange(newValue.selection.start.coerceIn(0, newValue.text.length),newValue.selection.end.coerceIn(0, newValue.text.length))
//                )
//            }
            if(newValue.length <= 10){
                inputValue.value = newValue
            }

        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                Toast.makeText(context, inputValue.value, Toast.LENGTH_SHORT).show()
                // 뒤로가기 버튼을 눌렀을 때 처리할 내용을 여기에 작성하세요.
            }
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth().height(100.dp)
    )
}

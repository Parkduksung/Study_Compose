package com.example.dialog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialog.ui.theme.StudyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CustomAlertDialog(
                        onConfirm = {},
                        onDismiss = {}
                    )
                }
            }
        }
    }
}


@Composable
fun CustomAlertDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {

        },
        title = {
            Text(text = "삭제하시겠습니까?")
        },
        text = {
            Text(text = "저장하신 메모가 삭제됩니다.")
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = {
                    onConfirm()
                }) {
                    Text(text = "확인")
                }
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(text = "확인")
                }
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}
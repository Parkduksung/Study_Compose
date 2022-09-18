package com.example.part1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.part1.ui.theme.StudyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(list: List<String> = listOf("World", "Compose")) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (item in list) {
            Greeting(name = item)
        }
    }
}

@Composable
fun Greeting(name: String) {

    val expanded = remember { mutableStateOf(false) }

    val expandableDp = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = expandableDp)
            ) {
                Text(text = "Hello")
                Text(text = "$name!")
            }
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }) {
                Text(text = if (expanded.value) "Show Less" else "Show More")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(modifier: Modifier = Modifier) {
    StudyComposeTheme {
        App()
    }
}
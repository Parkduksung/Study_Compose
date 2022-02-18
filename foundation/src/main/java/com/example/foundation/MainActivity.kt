package com.example.foundation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.foundation.ui.theme.StudyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    BoxExample()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudyComposeTheme {
        Greeting("Android")
    }
}


//Modifier.fillMaxWidth(Float.MAX_VALUE).fillMaxHeight(Float.MAX_VALUE) = fillMaxSize(Float.MAX_VALUE)
@Composable
fun BoxExample() {
    Box(Modifier.fillMaxSize(Float.MAX_VALUE)) {
        Text("안녕하세요.", modifier = Modifier.align(Alignment.Center))
        Text("안녕하세요.", modifier = Modifier.align(Alignment.TopCenter))
        Text("안녕하세요.", modifier = Modifier.align(Alignment.BottomCenter))
    }
}
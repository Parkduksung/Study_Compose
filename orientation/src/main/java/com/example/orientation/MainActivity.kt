package com.example.orientation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import com.example.orientation.ui.theme.StudyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OrientationScreen()
                }
            }
        }
    }
}

@Composable
fun OrientationScreen() {

    val orientationType =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            OrientationType.LANDSCAPE
        } else {
            OrientationType.PORTRAIT
        }

    OrientationItem(
        modifier = Modifier.fillMaxSize(),
        item = orientationType
    )
}

@Composable
fun OrientationItem(
    modifier: Modifier = Modifier,
    item: OrientationType
) {
    Box(modifier = modifier.background(item.backgroundColor), contentAlignment = Alignment.Center) {
        Text(text = item.name, color = item.fontColor)
    }
}

enum class OrientationType(
    val type: String,
    val fontColor: Color,
    val backgroundColor: Color
) {
    PORTRAIT(
        "Portrait",
        Color.White,
        Color.Blue
    ),
    LANDSCAPE(
        "Landscape",
        Color.White,
        Color.Red
    )
}

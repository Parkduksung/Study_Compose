package com.example.piechart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.piechart.ui.theme.StudyComposeTheme

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
                    val data = listOf(0.4f, 0.3f, 0.2f, 0.1f)
                    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        PieChart(
                            data = data,
                            colors = colors,
                            modifier = Modifier
                                .size(200.dp),
                            strokeWidth = 32.dp
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PieChart(
    data: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 24.dp,
    startAngle: Float = -90f
) {
    val sweepAngles = data.map { it * 360f }
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val halfSize = size.minDimension / 2
        val radius = halfSize - strokeWidth.toPx() / 2
        val centerX = halfSize
        val centerY = halfSize
        var currentStartAngle = startAngle

        for (i in sweepAngles.indices) {
            drawArc(
                color = colors[i],
                startAngle = currentStartAngle,
                sweepAngle = sweepAngles[i],
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = size,
                style = Stroke(width = strokeWidth.toPx())
            )
            currentStartAngle += sweepAngles[i]
        }
    }
}
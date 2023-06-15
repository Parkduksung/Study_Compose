package com.example.canvas

import android.graphics.Typeface
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import com.example.canvas.ui.theme.StudyComposeTheme

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

                }
            }
        }
    }
}

//insta logo
@Preview(showBackground = true)
@Composable
fun CanvasPractice1(modifier: Modifier = Modifier) {
    val instaColors = listOf(Color.Yellow, Color.Red, Color.Magenta)

    Canvas(modifier = modifier
        .size(100.dp)
        .padding(16.dp), onDraw = {

        drawRoundRect(
            brush = Brush.linearGradient(instaColors),
            cornerRadius = CornerRadius(60f, 60f),
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )

        drawCircle(
            brush = Brush.linearGradient(instaColors),
            radius = 45f,
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )
        drawCircle(
            brush = Brush.linearGradient(instaColors),
            radius = 13f,
            center = Offset(this.size.width * 0.8f, this.size.height * 0.2f)
        )
    })
}

@Preview(showBackground = true)
@Composable
fun CanvasPractice2(modifier: Modifier = Modifier) {
    val colors = listOf(Color(0xFF02b8f9), Color(0xFF0277fe))

    Canvas(modifier = modifier
        .size(100.dp)
        .padding(16.dp), onDraw = {

        val trianglePath = Path().let {
            it.moveTo(this.size.width * 0.2f, this.size.height * 0.77f)
            it.lineTo(this.size.width * 0.2f, this.size.height * 0.95f)
            it.lineTo(this.size.width * .37f, this.size.height * 0.86f)
            it.close()
            it
        }

        val electricPath = Path().let {
            it.moveTo(this.size.width * .20f, this.size.height * 0.60f)
            it.lineTo(this.size.width * .45f, this.size.height * 0.35f)
            it.lineTo(this.size.width * 0.56f, this.size.height * 0.46f)
            it.lineTo(this.size.width * 0.78f, this.size.height * 0.35f)
            it.lineTo(this.size.width * 0.54f, this.size.height * 0.60f)
            it.lineTo(this.size.width * 0.43f, this.size.height * 0.45f)
            it.close()
            it
        }

        drawOval(
            Brush.verticalGradient(colors = colors),
            size = Size(this.size.width, this.size.height * 0.95f)
        )

        drawPath(
            path = trianglePath,
            Brush.verticalGradient(colors = colors),
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )

        drawPath(path = electricPath, color = Color.White)
    })
}


@Preview(showBackground = true)
@Composable
fun CanvasPractice3(modifier: Modifier = Modifier) {

    Canvas(modifier = modifier
        .size(100.dp)
        .padding(16.dp), onDraw = {

        drawRoundRect(
            color = Color.Red,
            cornerRadius = CornerRadius(20f, 20f),
            size = Size(200f, 100f)
        )

        val trianglePath = Path().let {
            it.moveTo(this.size.width * 0.5f - 10, this.size.height * 0.15f)
            it.lineTo(this.size.width * 0.5f - 10, this.size.height * 0.35f)
            it.lineTo(this.size.width * 0.5f + 30, this.size.height * 0.25f)
            it.close()
            it
        }

        drawPath(
            path = trianglePath,
            color = Color.White
        )
    })
}

@Preview
@Composable
fun CanvasPractice4(modifier: Modifier = Modifier) {

    Canvas(modifier = modifier
        .size(100.dp)
        .padding(16.dp), onDraw = {

        drawArc(
            color = Color(0xFFf04231),
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .25f, 0f)
        )

        drawArc(
            color = Color(0xFF4385f7),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .5f, size.height * .25f)
        )

        drawArc(
            color = Color(0xFF30a952),
            startAngle = 0f,
            sweepAngle = -180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .0f, size.height * .25f)
        )

        drawArc(
            color = Color(0xFFffbf00),
            startAngle = 270f,
            sweepAngle = -180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .25f, size.height * .5f)
        )

    })
}

@Preview
@Composable
fun CanvasPractice5(modifier: Modifier = Modifier) {

    val backgroundColor = listOf(Color(0xFF2078EE), Color(0xFF74E6FE))
    val sunColor = listOf(Color(0xFFFFC200), Color(0xFFFFE100))

    Canvas(modifier = modifier
        .size(100.dp)
        .padding(16.dp), onDraw = {

        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(width.times(.76f), height.times(.72f))
            cubicTo(
                width.times(.93f),
                height.times(.72f),
                width.times(.98f),
                height.times(.41f),
                width.times(.76f),
                height.times(.40f)
            )
            cubicTo(
                width.times(.75f),
                height.times(.21f),
                width.times(.35f),
                height.times(.21f),
                width.times(.38f),
                height.times(.50f)
            )
            cubicTo(
                width.times(.25f),
                height.times(.50f),
                width.times(.20f),
                height.times(.69f),
                width.times(.41f),
                height.times(.72f)
            )
            close()
        }

        drawRoundRect(
            brush = Brush.verticalGradient(backgroundColor),
            cornerRadius = CornerRadius(50f, 50f),

            )
        drawCircle(
            brush = Brush.verticalGradient(sunColor),
            radius = width.times(.17f),
            center = Offset(width.times(.35f), height.times(.35f))
        )
        drawPath(path = path, color = Color.White.copy(alpha = .90f))
    })
}

@Preview
@Composable
fun CanvasPractice6(modifier: Modifier = Modifier) {

    Canvas(modifier = modifier
        .size(100.dp)
        .padding(20.dp), onDraw = {

        val width = this.size.width
        val height = this.size.height

        val path = Path()
        path.moveTo(width / 2, height / 2)
        path.lineTo(width + 5, height * .05f)
        path.lineTo(width + 20, height * 0.45f)


        clipPath(path = path, clipOp = ClipOp.Difference) {
            drawCircle(
                color = Color.Transparent,
                radius = 100f,
                style = Stroke(40f, cap = StrokeCap.Round)
            )
            drawPath(path = path, color = Color.Transparent)
        }

        drawRect(
            color = Color(0xFF4384f3),
            size = Size(width * .55f, 20f),
            topLeft = Offset(width * .55f, height * .45f)
        )

        drawRect(
            color = Color(0xFF4384f3),
            size = Size(width * .45f, 20f),
            topLeft = Offset(width * .55f, height * .54f)
        )

        drawArc(
            color = Color(0xFF4384f3),
            startAngle = -4.8f,
            sweepAngle = 50f,
            useCenter = false,
            style = Stroke(width = 40f)
        )
        drawArc(
            color = Color(0xFF33a852),
            startAngle = 45f,
            sweepAngle = 135f,
            useCenter = false,
            style = Stroke(width = 40f)
        )
        drawArc(
            color = Color(0xFFfabd03),
            startAngle = 145f,
            sweepAngle = 80f,
            useCenter = false,
            style = Stroke(width = 40f)
        )

        drawArc(
            color = Color(0xFFeb4435),
            startAngle = 205f,
            sweepAngle = 120f,
            useCenter = false,
            style = Stroke(width = 40f)
        )

    })
}




@Preview
@Composable
fun CanvasPractice7(modifier: Modifier = Modifier) {

    val backgroundColor = listOf(Color(0xFF17bd45), Color(0xFF07EEB8))


    Canvas(modifier = modifier
        .size(100.dp)
        .padding(16.dp), onDraw = {

        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(width.times(.5f), height.times(.5f))
            cubicTo(
                width.times(.93f),
                height.times(.72f),
                width.times(.98f),
                height.times(.41f),
                width.times(.76f),
                height.times(.40f)
            )
            close()
        }

        drawRoundRect(
            brush = Brush.linearGradient(backgroundColor),
            cornerRadius = CornerRadius(50f, 50f),
        )

        drawCircle(
            Color.White,
            radius = 10f,
            center = Offset(width.times(.35f), height.times(.35f))
        )

        drawCircle(
            Color.White,
            radius = 10f,
            center = Offset(width.times(.65f), height.times(.35f))
        )

        drawPath(path = path, color = Color.White.copy(alpha = .90f))

    })
}


package com.example.stringtoimage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stringtoimage.MediaUtil.Companion.getBitmapFromUrl
import com.example.stringtoimage.MediaUtil.Companion.saveToGallery
import com.example.stringtoimage.ui.theme.StudyComposeTheme
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

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
                    CoilSample("https://images.dog.ceo/breeds/saluki/n02091831_3400.jpg")
                }
            }
        }

    }

}

@Composable
fun CoilSample(url: String) {

    val context = LocalContext.current


    Column(modifier = Modifier.fillMaxSize()) {
        CoilImage(
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .clickable {
                    with(context) {
                        getBitmapFromUrl(url) { bitmap ->
                            bitmap?.saveToGallery(this@with)
                        }
                    }
                },
            imageModel = url,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = Color.Black,
                durationMillis = 1000,
                dropOff = 0.65f,
                tilt = 20f
            ),
            contentScale = ContentScale.Crop,
            failure = {
                Text(text = "image request failed.")
            })
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudyComposeTheme {
        CoilSample("Android")
    }
}




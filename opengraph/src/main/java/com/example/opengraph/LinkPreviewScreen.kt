package com.example.opengraph

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkPreviewScreen(url: String) {
    val linkPreviewState = remember(url) { mutableStateOf(LinkPreviewState()) }

    LaunchedEffect(url) {
        fetchLinkPreview(url)?.let {
            linkPreviewState.value = it
            Log.d("결과",it.toString())
        }
    }

    val context = LocalContext.current

    Scaffold(
        content = { padding ->
            Column(Modifier.padding(16.dp)) {
                linkPreviewState.value.image?.let { imageUrl ->
                    CoilImage(
                        imageRequest = ImageRequest.Builder(context).data(imageUrl).build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(shape = RoundedCornerShape(4.dp))
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Text(
                    text = linkPreviewState.value.title ?: "",
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = linkPreviewState.value.description ?: "",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    )
}

package com.example.collapsingtoolbar


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val lazyListState = rememberLazyListState()
            var scrolledY = 0f
            var previousOffset = 0

            val items = (1..100).map { "Item $it" }

            LazyColumn(
                Modifier.fillMaxSize(),
                lazyListState,
            ) {

                item {

                    //todo 여기서 커스텀하면됨.
                    Image(
                        painter = painterResource(id = R.drawable.sample_image),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .graphicsLayer {
                                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                                translationY = scrolledY * 0.5f
                                previousOffset =
                                    lazyListState.firstVisibleItemScrollOffset
                            }
                            .height(240.dp)
                            .fillMaxWidth()
                    )
                }

                // Displaying the remaining 100 items
                items(items) {
                    Text(
                        text = it,
                        Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

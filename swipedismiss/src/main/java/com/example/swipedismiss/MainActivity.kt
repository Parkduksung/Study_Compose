package com.example.swipedismiss

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.swipedismiss.ui.theme.StudyComposeTheme

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
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(styleUrlList) {
                            StyledCard(modifier = Modifier.padding(16.dp), styledUrl = it) {

                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val styleUrlList = mutableListOf(
            StyledUrl("abc"),
            StyledUrl("def"),
        )
    }
}

data class StyledUrl(
    val url: String
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StyledCard(
    modifier: Modifier,
    styledUrl: StyledUrl,
    onDismissedToDelete: (styledUrl: StyledUrl) -> Unit,
) {
    val context = LocalContext.current
    val shape = RoundedCornerShape(30.dp)
    val backgroundColor = Color(240, 240, 240)
    val dismissState = rememberDismissState(confirmStateChange = { dismissValue ->
        when (dismissValue) {
            DismissValue.Default -> { // dismissThresholds 만족 안한 상태
                false
            }
            DismissValue.DismissedToEnd -> { // -> 방향 스와이프 (수정)
                Toast.makeText(context, "수정", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }
    })


    SwipeToDismissStart(
        state = dismissState,
        dismissThresholds = { FractionalThreshold(0.10f) },
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(shape),
        dismissContent = { // content
            CarrotUrlCard(
                styledUrl = styledUrl,
                backgroundColor = backgroundColor,
                shape = shape
            )
        }
    )

}


@Composable
fun CarrotUrlCard(styledUrl: StyledUrl, backgroundColor: Color, shape: RoundedCornerShape) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(backgroundColor, shape)
    ) {
        Text(text = styledUrl.url)
    }
}



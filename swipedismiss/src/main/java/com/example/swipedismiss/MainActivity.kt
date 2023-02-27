package com.example.swipedismiss

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
            DismissValue.DismissedToStart -> { // <- 방향 스와이프 (삭제)
                onDismissedToDelete(styledUrl)
                true
            }
        }
    })

    SwipeToDismiss(
        state = dismissState,
        dismissThresholds = { FractionalThreshold(0.10f) },
        directions = setOf(DismissDirection.StartToEnd),
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
        },
        background = { // dismiss content
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> backgroundColor.copy(alpha = 0.5f) // dismissThresholds 만족 안한 상태
                    DismissValue.DismissedToEnd -> Color.Green.copy(alpha = 0.4f) // -> 방향 스와이프 (수정)
                    DismissValue.DismissedToStart -> Color.Red.copy(alpha = 0.5f) // <- 방향 스와이프 (삭제)
                }
            )
            val icon = when (dismissState.targetValue) {
                DismissValue.Default -> painterResource(android.R.drawable.ic_dialog_email)
                DismissValue.DismissedToEnd -> painterResource(android.R.drawable.ic_dialog_info)
                DismissValue.DismissedToStart -> painterResource(android.R.drawable.ic_dialog_map)
            }
            val scale by animateFloatAsState(
                when (dismissState.targetValue == DismissValue.Default) {
                    true -> 0.8f
                    else -> 1.5f
                }
            )
            val alignment = when (direction) {
                DismissDirection.EndToStart -> Alignment.CenterEnd
                DismissDirection.StartToEnd -> Alignment.CenterStart
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
                    .background(color)
                    .padding(horizontal = 30.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    modifier = Modifier.scale(scale),
                    painter = icon,
                    contentDescription = null
                )
            }
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



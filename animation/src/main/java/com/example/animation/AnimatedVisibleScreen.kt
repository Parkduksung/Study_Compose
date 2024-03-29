package com.example.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


/**
 * 시각적 애니메이션
 *
 * AnimatedVisibility
 *
 * Use Situations
 * Animating content change in layout - OK
 * Animating apperance and disappearance - OK
 *
 * AnimatedContent or Crossfade
 *
 * Use Situations
 * Animating content change in layout - OK
 * Animating apperance and disappearance - NO
 * Swapping content based on state - OK
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibleScreen() {

    var boxVisible by remember { mutableStateOf(true) }

    val onClick = { newState: Boolean ->
        boxVisible = newState
    }


//    val state = remember { MutableTransitionState(false) }
//
//    //자동시작할때 이렇게도 가능.
//    LaunchedEffect(key1 = Unit) {
//        state.targetState = true
//    }
//
//    //다 끝났을때가 Idle 상태
//    if (state.isIdle) {
//        state.targetState = false
//    }


    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Crossfade(
                targetState = boxVisible,
                animationSpec = tween(durationMillis = 1500), label = "Crossfade CustomButton"
            ) { visible ->
                when (visible) {
                    true -> CustomButton(
                        text = "Hide",
                        targetState = false,
                        onClick = onClick,
                        bgColor = Color.Red
                    )

                    false -> CustomButton(
                        text = "Show",
                        targetState = true,
                        onClick = onClick,
                        bgColor = Color.Magenta
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedVisibility(
            visible = boxVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1500))
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.Blue)
                )

                Spacer(modifier = Modifier.width(20.dp))

                // enter 는 fadeIn + slideInVertically
                // exit 는 fadeOut + slideOutVertically
                Box(
                    modifier = Modifier
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = tween(durationMillis = 1500),
                                initialOffsetY = { -it }),
                            exit = slideOutVertically(
                                animationSpec = tween(durationMillis = 1500),
                                targetOffsetY = { it }
                            )
                        )
                        .size(150.dp)
                        .background(Color.Blue)
                )
            }

        }
    }

}

@Composable
fun CustomButton(
    text: String,
    targetState: Boolean,
    onClick: (Boolean) -> Unit,
    bgColor: Color = Color.Blue
) {

    Button(
        onClick = { onClick(targetState) },
        colors = ButtonDefaults.buttonColors(containerColor = bgColor, contentColor = Color.White)
    ) {
        Text(text = text)
    }

}

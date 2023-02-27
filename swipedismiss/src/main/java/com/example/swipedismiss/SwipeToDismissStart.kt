package com.example.swipedismiss

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
@ExperimentalMaterialApi
fun SwipeToDismissStart(
    state: DismissState,
    modifier: Modifier = Modifier,
    dismissThresholds: (DismissDirection) -> ThresholdConfig = {
        FractionalThreshold(
            0.25f
        )
    },
    dismissContent: @Composable RowScope.() -> Unit
) = BoxWithConstraints(modifier) {


    val width = constraints.maxWidth.toFloat() * 0.3f

    val anchors = mutableMapOf(0f to DismissValue.Default)
    anchors += width to DismissValue.DismissedToEnd

    val thresholds =
        { from: DismissValue, to: DismissValue ->
            dismissThresholds(DismissDirection.StartToEnd)
        }



    Box(
        Modifier.swipeable(
            state = state,
            anchors = anchors,
            thresholds = thresholds,
            orientation = Orientation.Horizontal,
            enabled = state.currentValue == DismissValue.Default,
            resistance = ResistanceConfig(
                basis = width,
                factorAtMin = 0f,
                factorAtMax = 0f
            )
        )
    ) {

        Row(
            modifier = Modifier
                .matchParentSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(width.dp)
                    .background(Color.Green.copy(alpha = 0.4f))
                    .padding(horizontal = 30.dp),
            ) {
                Icon(
                    modifier = Modifier
                        .scale(1.5f)
                        .align(CenterStart),
                    painter = painterResource(R.drawable.ic_dialog_info),
                    contentDescription = null
                )
            }
        }
        Row(
            content = dismissContent,
            modifier = Modifier.offset { IntOffset(state.offset.value.roundToInt(), 0) }
        )
    }
}

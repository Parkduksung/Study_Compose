package com.example.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.padding
import androidx.glance.layout.size
import com.example.widget.R


@Composable
fun StartButton(
    modifier: GlanceModifier = GlanceModifier,
    onClick : () -> Unit
){
    Image(
        provider = ImageProvider(R.drawable.baseline_send_to_mobile_24),
        contentDescription = "Close",
        modifier = modifier.size(64.dp).clickable {
            onClick()
        }.padding(8.dp).background(Color.White)
    )
}
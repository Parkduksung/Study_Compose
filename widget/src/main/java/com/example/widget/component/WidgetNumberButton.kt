package com.example.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.layout.Alignment
import androidx.glance.layout.size
import androidx.glance.text.Text


@Composable
internal fun NumberButton(
    number: String,
    onClick: () -> Unit
) {

    androidx.glance.layout.Box(
        modifier = GlanceModifier.size(40.dp).clickable {
            onClick()
        },
        contentAlignment = Alignment.Center
    ) {
        Text(text = number)
    }

}
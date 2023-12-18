package com.example.widget

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

class WhatsAppGlanceWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WhatsAppGlanceWidget()
}

class WhatsAppGlanceWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            var editing by remember { mutableStateOf(false) }
            androidx.glance.layout.Column {
                Text(
                    text = if (editing) "Editing" else "Not Editing", style = TextStyle(
                        color = ColorProvider(
                            Color.Green
                        )
                    )
                )
                androidx.glance.Button(text = "Click Me", onClick = {
                    editing = !editing
                })
            }

        }
    }

}

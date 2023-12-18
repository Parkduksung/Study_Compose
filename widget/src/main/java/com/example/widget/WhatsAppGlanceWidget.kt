package com.example.widget

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.example.widget.component.StartButton
import com.example.widget.component.WidgetInputNumber


class WhatsAppGlanceWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            var isStart by remember { mutableStateOf(false) }

            var mobileNumber by remember { mutableStateOf("") }

            if (isStart) {
                WidgetInputNumber(
                    mobileNumber = mobileNumber,
                    onUpdate = {
                        mobileNumber = it
                    },
                    onRouteSms = {
                        actionSendToWhatsApp(context, it)
                    },
                    onClose = {
                        isStart = false
                    }
                )
            } else {
                StartButton {
                    isStart = true
                }
            }
        }
    }
}

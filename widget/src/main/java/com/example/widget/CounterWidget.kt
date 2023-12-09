package com.example.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.example.widget.ui.theme.StudyComposeTheme

class CounterWidget : GlanceAppWidget() {

    val countKey = intPreferencesKey("count")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            StudyComposeTheme {
                Widget()
            }
        }
    }

    @Composable
    private fun Widget() {
        Column(
            modifier = GlanceModifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Vertical.Top,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Text(
                text ="Hello Glance!",
                modifier = GlanceModifier.padding(12.dp)
            )
//            Button(
//                text = "Inc",
//                onClick = actionRunCallback(IncrementActionCallback::class.java)
//            )
        }
    }


}


class SimpleCounterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = CounterWidget()
}

class IncrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
//        updateAppWidgetState(context, glanceId) { prefs ->
//            val currentCount = prefs[CounterWidget.countKey]
//            if (currentCount != null) {
//                prefs[CounterWidget.countKey] = currentCount + 1
//            } else {
//                prefs[CounterWidget.countKey] = 1
//            }
//        }
//        CounterWidget.update(context, glanceId)
    }
}
package com.example.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent

fun sendWidgetUpdateCommand(context: Context) {
    context.sendBroadcast(
        Intent(
            context,
            WhatsAppGlanceWidgetReceiver::class.java
        ).setAction(
            AppWidgetManager.ACTION_APPWIDGET_UPDATE
        )
    )
}
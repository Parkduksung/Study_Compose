package com.example.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.glance.action.Action


internal fun actionSendToWhatsApp(context: Context, mobileNumber: String) {
    context.startActivity(
        Intent(
            Intent.ACTION_SENDTO, Uri.parse("sms:$mobileNumber")
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}


internal fun actionLaunchIntentForPackage(context: Context): Action =
    androidx.glance.appwidget.action.actionStartActivity(
        Intent(
            context.packageManager.getLaunchIntentForPackage(
                context.packageName
            )
        )
    )
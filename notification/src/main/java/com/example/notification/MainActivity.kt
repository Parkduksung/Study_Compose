package com.example.notification

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}


fun Context.getImageUri(): Uri {
    val resources = this.resources

    return Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(R.drawable.android_cupcake))
        .appendPath(resources.getResourceTypeName(R.drawable.android_cupcake))
        .appendPath(resources.getResourceEntryName(R.drawable.android_cupcake))
        .build()
}

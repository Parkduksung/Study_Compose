package com.example.swipedismiss

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity

fun ComponentActivity.toast(
    context: Context,
    message: (String) -> Unit = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
) {
    //더하고 싶은 무언가가 있으면 여기서 하셈.
}
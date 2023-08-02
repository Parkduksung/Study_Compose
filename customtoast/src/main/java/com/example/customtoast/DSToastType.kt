package com.example.customtoast

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

enum class DSToastType(
    @DrawableRes val icon: Int? = null,
    val backgroundColor: Color
) {
    BASIC(backgroundColor = Color.LIGHT_GRAY),
    INFO(backgroundColor = Color.LIGHT_GRAY),
    SUCCESS(backgroundColor = Color.LIGHT_GRAY),
    ERROR(backgroundColor = Color.LIGHT_GRAY)
}
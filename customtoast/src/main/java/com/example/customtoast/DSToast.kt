package com.example.customtoast

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColor
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class DSToast(context: Context) : Toast(context) {

    companion object {
        operator fun invoke(
            context: Context,
            message: String,
            duration: Int = LENGTH_SHORT,
            type: RSToastType = RSToastType.BASIC,
        ): DSToast {

            val sweetToast = DSToast(context)

            val views = ComposeView(context).apply {
                setViewTreeLifecycleOwner(context as LifecycleOwner)
                setViewTreeViewModelStoreOwner(context as ViewModelStoreOwner)
                setViewTreeSavedStateRegistryOwner(context as SavedStateRegistryOwner)
                setContent {
                    RSToastContent(message = message, type = type)
                }
            }
            sweetToast.duration = duration
            sweetToast.view = views

            return sweetToast

        }
    }
}

@Composable
internal fun RSToastContent(
    message: String,
    type: RSToastType,
) {

    Row(
        Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 44.dp)
            .background(type.backgroundColor, RoundedCornerShape(22.dp))
            .padding(horizontal = 20.dp, vertical = 11.dp)
    ) {


        if (type.icon != null) {
            Box(
                modifier = Modifier.size(20.dp),
                contentAlignment = Alignment.Center
            ) {
                type.icon.toPainter.let {
                    Image(painter = it, contentDescription = null, Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.width(10.dp))
        }


        Text(text = message, fontSize = 16.scaledSp(), color = Color.Black)

    }
}

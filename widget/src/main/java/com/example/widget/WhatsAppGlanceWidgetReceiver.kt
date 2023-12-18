package com.example.widget

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.lazy.GridCells
import androidx.glance.appwidget.lazy.LazyVerticalGrid
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.Text

class WhatsAppGlanceWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WhatsAppGlanceWidget()
}

class WhatsAppGlanceWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            var isStart by remember { mutableStateOf(false) }

            var mobileNumber by remember { mutableStateOf("") }

            if (isStart) {
                androidx.glance.layout.Column(
                    modifier = GlanceModifier.background(Color.White).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = mobileNumber)

                    LazyVerticalGrid(gridCells = GridCells.Fixed(3), content = {
                        items((1..12).toList()) { num ->

                            when (num) {
                                10 -> {
                                    Image(
                                        provider = ImageProvider(R.drawable.baseline_cleaning_services_24),
                                        contentDescription = "Clean",
                                        modifier = GlanceModifier.size(40.dp).clickable {
                                            mobileNumber = ""
                                        }.padding(8.dp)
                                    )
                                }

                                11 -> {
                                    NumberButton(number = "0") {
                                        mobileNumber += "0"
                                    }
                                }

                                12 -> {
                                    Image(
                                        provider = ImageProvider(R.drawable.baseline_arrow_back_24),
                                        contentDescription = "Back",
                                        modifier = GlanceModifier.size(40.dp).clickable {
                                            mobileNumber = mobileNumber.dropLast(1).toString()
                                        }.padding(8.dp)
                                    )
                                }

                                else -> {
                                    NumberButton(number = "$num") {
                                        Log.d("결과", "$num")
                                        mobileNumber += "$num"
                                    }
                                }
                            }
                        }
                    })

                    Row(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Image(
                            provider = ImageProvider(R.drawable.baseline_send_24),
                            contentDescription = "Send",
                            modifier = GlanceModifier.width(60.dp).height(40.dp).clickable {
                                sendToWhatsApp(context, mobileNumber)
                                Toast.makeText(context, "전송 완료", Toast.LENGTH_SHORT).show()
                                mobileNumber = ""
                                isStart = false
                            }.padding(8.dp)
                        )

                        Image(
                            provider = ImageProvider(R.drawable.baseline_power_settings_new_24),
                            contentDescription = "Close",
                            modifier = GlanceModifier.width(60.dp).height(40.dp).clickable {
                                isStart = false
                            }.padding(8.dp)
                        )
                    }
                }
            } else {
                Image(
                    provider = ImageProvider(R.drawable.baseline_send_to_mobile_24),
                    contentDescription = "Close",
                    modifier = GlanceModifier.size(64.dp).clickable {
                        isStart = true
                    }.padding(8.dp).background(Color.White)
                )
            }
        }
    }

    private fun sendToWhatsApp(context: Context, mobileNumber: String) {
        val smsUri = Uri.parse("sms:$mobileNumber")
        val intent = Intent(Intent.ACTION_SENDTO, smsUri)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }


    @Composable
    fun NumberButton(
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

}

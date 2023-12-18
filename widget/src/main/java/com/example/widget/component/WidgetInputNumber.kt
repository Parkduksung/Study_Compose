package com.example.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.lazy.GridCells
import androidx.glance.appwidget.lazy.LazyVerticalGrid
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.Text
import com.example.widget.R

@Composable
fun WidgetInputNumber(
    modifier: GlanceModifier = GlanceModifier,
    mobileNumber: String,
    onUpdate: (String) -> Unit,
    onRouteSms: (String) -> Unit,
    onClose: () -> Unit
) {
    androidx.glance.layout.Column(
        modifier = modifier.background(Color.White).padding(16.dp),
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
                                onUpdate("")
                            }.padding(8.dp)
                        )
                    }

                    11 -> {
                        NumberButton(number = "0") {
                            onUpdate("0")
                        }
                    }

                    12 -> {
                        Image(
                            provider = ImageProvider(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Back",
                            modifier = GlanceModifier.size(40.dp).clickable {
                                onUpdate(mobileNumber.dropLast(1))
                            }.padding(8.dp)
                        )
                    }

                    else -> {
                        NumberButton(number = "$num") {
                            onUpdate("$mobileNumber$num")
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
                    onRouteSms(mobileNumber)
                    onUpdate("")
                    onClose()
                }.padding(8.dp)
            )

            Image(
                provider = ImageProvider(R.drawable.baseline_power_settings_new_24),
                contentDescription = "Close",
                modifier = GlanceModifier.width(60.dp).height(40.dp).clickable {
                    onClose()
                }.padding(8.dp)
            )
        }
    }
}

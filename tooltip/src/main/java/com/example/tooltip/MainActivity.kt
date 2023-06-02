package com.example.tooltip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.tooltip.popup.CustomPopup
import com.example.tooltip.popup.PopupState
import com.example.tooltip.ui.theme.StudyComposeTheme
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val popupState = remember { PopupState(false) }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconWithCustomPopup(
                                popupState = popupState,
                                content = {
                                    Box(
                                        Modifier
                                            .width(100.dp)
                                            .height(300.dp)
                                    ) {
                                        Text(
                                            text = "테스트",
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }

                                },
                            )

                            IconWithCustomPopup(
                                popupState = popupState,
                                content = {
                                    Box(
                                        Modifier
                                            .width(100.dp)
                                            .height(300.dp)
                                    ) {
                                        Text(
                                            text = "테스트",
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }

                                },
                            )

                            IconWithCustomPopup(
                                popupState = popupState,
                                content = {
                                    Box(
                                        Modifier
                                            .width(100.dp)
                                            .height(300.dp)
                                    ) {
                                        Text(
                                            text = "테스트",
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }

                                },
                            )
                        }


                    }

                }
            }
        }
    }

    @Composable
    private fun IconWithCustomPopup(
        popupState: PopupState = remember {
            PopupState(false)
        },
        content: @Composable () -> Unit = {},
    ) {
        Box {
            CustomPopup(
                popupState = popupState,
                onDismissRequest = {
                    popupState.isVisible = false
                },
//                offset = DpOffset(10.dp,30.dp)
            ) {
                content()
            }
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info Icon",
                modifier = Modifier.clickable {
                    popupState.isVisible = !popupState.isVisible
                }
            )
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun PlainTooltipWithManualInvocationSample() {
    val tooltipState = remember { PlainTooltipState() }
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlainTooltipBox(
            tooltip = { Text("Add to list") },
            tooltipState = tooltipState
        ) {
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "Localized Description"
            )
        }
        Spacer(Modifier.requiredHeight(30.dp))
        OutlinedButton(
            onClick = { scope.launch { tooltipState.show() } }
        ) {
            Text("Display tooltip")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTooltipWithManualInvocationSample() {
    val tooltipState = remember { RichTooltipState() }
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RichTooltipBox(
            title = { Text("richTooltipSubheadText") },
            action = {
                TextButton(
                    onClick = {
                        scope.launch {
                            tooltipState.dismiss()
                        }
                    }
                ) { Text("richTooltipActionText") }
            },
            text = { Text("richTooltipText") },
            tooltipState = tooltipState
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Localized Description"
            )
        }
        Spacer(Modifier.requiredHeight(30.dp))
        OutlinedButton(
            onClick = { scope.launch { tooltipState.show() } }
        ) {
            Text("Display tooltip")
        }
    }
}
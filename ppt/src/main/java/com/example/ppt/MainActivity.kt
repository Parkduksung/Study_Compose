package com.example.ppt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.ppt.foundation.slide.Slide
import com.example.ppt.foundation.list.List
import com.example.ppt.foundation.text.LocalTextStyle
import com.example.ppt.foundation.text.Text
import com.example.ppt.foundation.text.TextStyle
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            runComposePPT(presentationFileName = "composePPTSample") {
                Slide(title = "ComposePPT") {

                    LaunchedEffect(Unit) {
                        delay(100)
                        println("You can use side effects with composePPT 💪")
                    }

                    List {
                        Text(
                            text = "ComposePPT is a UI toolkit to create PowerPoint " +
                                    "presentations with Compose 🤩"
                        )
                        Text(
                            text = "ComposePPT uses Compose Runtime under the hood."
                        )
                    }
                }

                Slide(title = "runComposePPT") {
                    Text(
                        text = "You can create as many presentations as " +
                                "you wish using runComposePPT."
                    )
                }

                Slide {
                    Text(
                        text = "You can have a title for the slide as the previous slides " +
                                "or have a slide without a title just like this slide."
                    )
                }

                Slide(title = "TextStyle") {
                    List {
                        Text(text = "You can give a custom style to the text using the TextStyle")

                        Text(text = "Here is a text with defaults")

                        Text(
                            text = "Here is a text with font size 40",
                            style = TextStyle(fontSize = 40f)
                        )

                        Text(
                            text = "Here is a text with font size 30 and font color magenta",
                            style = TextStyle(
                                fontSize = 30f,
                                fontColor = Color.Magenta
                            )
                        )

                        val smallRedTextStyle = TextStyle(
                            fontSize = 10f,
                            fontColor = Color.Red
                        )

                        CompositionLocalProvider(
                            LocalTextStyle provides smallRedTextStyle
                        ) {
                            Text(
                                text = "This text is using the provided custom text style through " +
                                        "LocalTextStyle composition local."
                            )
                            Text(
                                text = "If you check this and above text element there is no style set " +
                                        "but they both have the same style which is different than default."
                            )
                        }
                    }
                }

                Slide("Side Effects") {
                    var textToUpdate by remember {
                        mutableStateOf("I will be updated in LaunchedEffect after some delay")
                    }

                    LaunchedEffect(Unit) {
                        delay(100)
                        textToUpdate =
                            "If you see this text, it means initial text is updated after some delay in LaunchedEffect 🎉"
                    }

                    List {
                        Text("You can use side effects (LaunchedEffect, SideEffect, DisposableEffect) with composePPT 🤩")
                        Text("The text below is read from a state value which is updated after some delay in LaunchedEffect")
                        Text(textToUpdate)
                    }
                }
            }
        }
    }
}
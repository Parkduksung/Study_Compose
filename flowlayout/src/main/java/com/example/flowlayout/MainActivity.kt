package com.example.flowlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flowlayout.ui.theme.StudyComposeTheme

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val list = remember { mutableStateListOf<String>() }

                    var input by remember { mutableStateOf("") }

                    Box(modifier = Modifier.fillMaxSize()) {
                        FlowRow(
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.Center),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            list.forEachIndexed { index, string ->
                                TagChip(string) {
                                    list.removeAt(index)
                                }
                            }

                            TextField(
                                value = input,
                                onValueChange = {
                                    input =
                                        if (it.contains(" ")) {
                                            list.add(it)
                                            ""
                                        } else {
                                            it
                                        }
                                },
                                modifier = Modifier.width(((input.length * 10) + 30).dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color(0xFFFFFFFF)
                                )
                            )
                        }


                    }
                }
            }
        }
    }
}

@Composable
fun TagChip(content: String, onRemove: () -> Unit) {

    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = Color(
                    0xFFE5E2F8
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .wrapContentWidth()
            .defaultMinSize(minHeight = 32.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = content,
                style = typography.body2,
                color = Color(0xFF616161),
                modifier = Modifier.padding(2.dp),
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(2.dp))

            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Remove",
                    tint = Color(0xFF616161)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTagChip() {
    TagChip("qwef") {

    }
}
package com.example.viewpager.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.viewpager.greenColor
import com.example.viewpager.usecase.GetRandomListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Composable
fun TabB(data: String, viewModel: TabBViewModel = hiltViewModel()) {
    var text by remember { mutableStateOf("") }


    val list = viewModel.getList.collectAsState(emptyList()).value

    // on below line we are creating a column
    Column(
        // in this column we are specifying modifier
        // and aligning it center of the screen on below lines.
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // in this column we are specifying the text
        Text(
            // on below line we are specifying the text message
            text = data,

            // on below line we are specifying the text style.
            style = MaterialTheme.typography.h5,

            // on below line we are specifying the text color
            color = greenColor,

            // on below line we are specifying the font weight
            fontWeight = FontWeight.Bold,

            //on below line we are specifying the text alignment.
            textAlign = TextAlign.Center
        )

        TextField(value = text, onValueChange = { text = it })

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list) {
                Text(
                    // on below line we are specifying the text message
                    text = it,

                    // on below line we are specifying the text style.
                    style = MaterialTheme.typography.h5,

                    // on below line we are specifying the text color
                    color = Color.Black,

                    // on below line we are specifying the font weight
                    fontWeight = FontWeight.Bold,

                    //on below line we are specifying the text alignment.
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


@HiltViewModel
class TabBViewModel @Inject constructor(getRandomListUseCase: GetRandomListUseCase) : ViewModel() {
    val getList = getRandomListUseCase()
}
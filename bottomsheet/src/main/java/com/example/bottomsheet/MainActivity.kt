package com.example.bottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bottomsheet.ui.theme.StudyComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyBottomSheet()
                }
            }
        }
    }
}

//@ExperimentalMaterialApi
//@Composable
//fun MyBottomSheet() {
//    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
//
//    val k by remember {
//        mutableStateOf(false)
//    }
//
//    val scope = rememberCoroutineScope()
//
//    ModalBottomSheetLayout(
//        sheetState = bottomSheetState,
//        sheetContent = {
//            // BottomSheet 내용 작성
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Text(text = "Bottom Sheet Content")
//                Button(onClick = {
//                    scope.launch {
//                        bottomSheetState.hide()
//                    }
//                }) {
//                    Text(text = "Close Bottom Sheet")
//                }
//            }
//        }
//    ) {
//        // BottomSheet 호출 버튼 작성
//        Button(onClick = {
//            scope.launch {
//                bottomSheetState.show()
//            }
//        }) {
//            Text(text = "Open Bottom Sheet")
//        }
//    }
//}

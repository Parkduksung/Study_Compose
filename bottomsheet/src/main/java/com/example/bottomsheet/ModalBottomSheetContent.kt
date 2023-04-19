package com.example.bottomsheet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyBottomSheet() {
    var bottomSheetClose by remember { mutableStateOf(false) }

    var fullScreenSheet by remember { mutableStateOf(false) }

    val sheetState = rememberBottomSheetState(
        initialValue = if (bottomSheetClose) BottomSheetValue.Collapsed else BottomSheetValue.Expanded
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val coroutineScope = rememberCoroutineScope()


    val height = LocalConfiguration.current.screenHeightDp * 0.85

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height.dp)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (sheetState.progress.to == BottomSheetValue.Collapsed) {
                        Box(
                            modifier = Modifier
                                .width(64.dp)
                                .height(4.dp)
                                .background(Color.Gray)
                        )
                    }

                    Button(onClick = {
                        coroutineScope.launch {
                            fullScreenSheet = true

                            if (!sheetState.isCollapsed) {
                                sheetState.collapse()
                            } else {
                                sheetState.expand()
                            }
                        }
                    }) {
                        Text(text = if (sheetState.progress.fraction < 1.0f) "확장" else "축소")
                    }

                    if (sheetState.isAnimationRunning) {
                        Log.d("결과", "여기찍힘")
                    }


                    if (sheetState.progress.to == BottomSheetValue.Collapsed) {
                        Text(text = "확장됨", modifier = Modifier.fillMaxSize())
                    } else {
                        Text(text = "기본화면", modifier = Modifier.fillMaxSize())
                    }

                    // 나머지 Bottom Sheet 내용

                }
            }
        },
        sheetPeekHeight = 0.dp
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        bottomSheetClose = false
                        sheetState.expand()
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "로그인")
            }
        }
        // 나머지 앱 내용
        Text(text = "여기에 앱의 주요 내용을 추가하세요")
    }
}
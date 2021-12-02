package com.example.week8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.week8.ui.components.RallyTopAppBar
import com.example.week8.ui.theme.RallyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentScreen: RallyScreen by rememberSaveable { mutableStateOf(RallyScreen.Overview) }
            RallyApp(currentScreen) { screen ->
                currentScreen = screen
            }
        }
    }
}

@Composable
fun RallyApp(currentScreen: RallyScreen, onTabSelected: (RallyScreen) -> Unit) {
    RallyTheme {
        val allScreens = RallyScreen.values().toList()

        Scaffold(
            topBar = {
                RallyTopAppBar(
                    allScreens = allScreens,
                    onTabSelected = onTabSelected,
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                currentScreen.content(onScreenChange = onTabSelected)
            }
        }
    }
}

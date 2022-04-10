package com.example.foundation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foundation.ui.theme.StudyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.ListScreen.route
                    ) {
                        composable(
                            route = Screen.ListScreen.route
                        ) {
                            ListScreen(navController = navController)
                        }

                        composable(
                            route = Screen.DetailScreen.route
                        ) {
                            DetailScreen(navController = navController)
                        }
                    }

                }
            }
        }

        runOnUiThread {  }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudyComposeTheme {
        Greeting("Android")
    }
}


//Modifier.fillMaxWidth(Float.MAX_VALUE).fillMaxHeight(Float.MAX_VALUE) = fillMaxSize(Float.MAX_VALUE)
@Composable
fun BoxExample() {
    Box(Modifier.fillMaxSize(Float.MAX_VALUE)) {
        Text("안녕하세요.", modifier = Modifier.align(Alignment.Center))
        Text("안녕하세요.", modifier = Modifier.align(Alignment.TopCenter))
        Text("안녕하세요.", modifier = Modifier.align(Alignment.BottomCenter))
    }
}


@Composable
fun ListScreen(
    navController: NavController,
    nums: List<String> = List(1000) { "$it" }
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = nums) { num ->
                ListItem(num = num, onItemClick = {
                    navController.navigate(Screen.DetailScreen.route)
                })
            }
        }
    }
}

@Composable
fun ListItem(
    num: String,
    onItemClick: (String) -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = {
                if (num.toInt() <= 10) {
                    onItemClick(num)
                }
            })
    ) {
        CardContent(num = num)
    }
}

@Composable
fun CardContent(
    num: String
) {

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {

            Text(text = "Hello,")
            Text(
                text = num,
                style = (MaterialTheme.typography.h4).copy(fontWeight = FontWeight.ExtraBold)
            )
        }
    }
}

@Composable
fun DetailScreen(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("DetailScreen.", modifier = Modifier.align(Alignment.Center))
    }
}
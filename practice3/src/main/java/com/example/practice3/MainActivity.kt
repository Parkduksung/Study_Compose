package com.example.practice3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.practice3.ui.theme.StudyComposeTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                    getLOLChamp()
                }
            }
        }
    }

    private fun getLOLChamp() {
        val jsonString = assets.open("LOLChamp.json").bufferedReader().use { it.readText() }

        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }

        val lolResponseFromJsonString = json.decodeFromString<LOLResponse<LOLChamp>>(jsonString)

        Log.d("결과", lolResponseFromJsonString.toString())

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


@Serializable
data class LOLResponse<T>(val type: String, val version: String, val data: Map<String, T>)

@Serializable
data class LOLChamp(
    val id: String,
    val name: String
)
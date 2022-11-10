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
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET


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

        CoroutineScope(Dispatchers.IO).launch {
            val json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            }

            val retrofit = Retrofit.Builder().baseUrl("https://ddragon.leagueoflegends.com/")
                .addConverterFactory(json.asConverterFactory(MediaType.parse("application/json")!!))
                .build()


            val service = retrofit.create(LOLService::class.java)

            val call = service.getLOLResponse()

            val response = call.execute()

            if(response.isSuccessful){
                val lolResponse = response.body()
                Log.d("결과", lolResponse.toString())
            }
        }

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
    val name: String,
    val imageUrl: String = name + "_0.jpg"
)


interface LOLService {

    @GET("cdn/11.16.1/data/en_US/champion.json")
    fun getLOLResponse(): Call<LOLResponse<LOLChamp>>
}
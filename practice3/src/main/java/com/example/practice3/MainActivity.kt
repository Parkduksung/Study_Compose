package com.example.practice3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practice3.ui.theme.StudyComposeTheme
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getLOLResponse { list ->
            list?.let {
                setContent {
                    StudyComposeTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            LOLListScreen(list = list)
                        }
                    }
                }
            }
        }
    }


    private fun getLOLResponse(callback: (List<LOLChamp>?) -> Unit) {
        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }

        val retrofit = Retrofit.Builder().baseUrl("https://ddragon.leagueoflegends.com/")
            .addConverterFactory(json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()


        val service = retrofit.create(LOLService::class.java)

        service.getLOLResponse().enqueue(object : Callback<LOLResponse<LOLChamp>> {
            override fun onResponse(
                call: Call<LOLResponse<LOLChamp>>,
                response: Response<LOLResponse<LOLChamp>>
            ) {
                callback(response.body()?.data?.values?.toList() ?: emptyList())
            }

            override fun onFailure(call: Call<LOLResponse<LOLChamp>>, t: Throwable) {
                callback(null)
            }
        })
    }
}

@Composable
fun LOLListScreen(list: List<LOLChamp>) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .semantics { testTag = "lazyCoinListColumn" }) {
        items(list) { champ ->
            LOLChampItem(
                champ = champ
            )
        }
    }
}


@Composable
fun LOLChampItem(champ: LOLChamp) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
            .height(150.dp)
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {

        CoilImage(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(80.dp)
                .width(80.dp),
            imageModel = champ.imageUrl,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = Color.Black,
                durationMillis = 1000,
                dropOff = 0.65f,
                tilt = 20f
            ),
            contentScale = ContentScale.Crop,
            failure = {
                Text(text = "image request failed.")
            })

        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = champ.name,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
        )
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
    val imageUrl: String = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + name + "_0.jpg"
)


interface LOLService {

    @GET("cdn/11.16.1/data/en_US/champion.json")
    fun getLOLResponse(): Call<LOLResponse<LOLChamp>>
}
package com.example.practice3

import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.lifecycleScope
import com.example.practice3.ui.theme.StudyComposeTheme
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.atomic.AtomicInteger


class MainActivity : ComponentActivity() {

    private val count = AtomicInteger(1)

    private val list = mutableSetOf<CoronaCenter>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            StudyComposeTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//
//
//                }
//            }
//        }

        lifecycleScope.launchWhenCreated {
            val success = withTimeoutOrNull(2000) {
                getCenterItem()
            }

            if (success == null) {
                Log.d("결과", "이어서 시작")
                getCenterItem()
            }
            Log.d("--결과--", list.size.toString())
        }
    }

    private suspend fun getCenterItem() {
        while (count.get() <= 10) {
            getCenter(count.get()).collect {
                list.addAll(it)
                count.set(count.get() + 1)
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


    private fun getCenter(page: Int): Flow<List<CoronaCenter>> = flow {

        try {
            val json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            }.apply {
                asConverterFactory(MediaType.parse("application/json")!!)
            }

            val retrofit = Retrofit.Builder().baseUrl("https://api.odcloud.kr/")
                .addConverterFactory(json.asConverterFactory(MediaType.parse("application/json")!!))
                .build()

            val service = retrofit.create(CoronaCenterService::class.java)

            val data = service.getCenter(page, 10).data

            emit(data)
        } catch (e: Exception) {
            Log.d("결과", e.toString())
            emit(emptyList())
        }

//        service.getCenter(page = 1, perPage = 10).enqueue(object : Callback<CoronaResponse> {
//            override fun onResponse(
//                call: Call<CoronaResponse>,
//                response: Response<CoronaResponse>
//            ) {
//                response.body()?.let { callback(it.data) } ?: callback(null)
//            }
//
//            override fun onFailure(call: Call<CoronaResponse>, t: Throwable) {
//                callback(null)
//            }
//        })

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
            imageModel = champ.imageUrl.trim(),
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
    val imageUrl: String = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + name.trim() + "_0.jpg"
)


interface LOLService {
    @GET("cdn/11.16.1/data/en_US/champion.json")
    fun getLOLResponse(): Call<LOLResponse<LOLChamp>>
}

interface CoronaCenterService {

    companion object {
        private const val SERVICE_KEY =
            "bNmSjmL3NWL%2FmAmsQV0SyDT%2B8DCdZckhVg5%2FtSsmJHa47eBZBE%2BaFvCHYxeM1Dsz2FcgQ64elqYL3mr6GUyjOg%3D%3D"
    }

    @GET("api/15077586/v1/centers")
    suspend fun getCenter(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey", encoded = true) serviceKey: String = SERVICE_KEY
    ): CoronaResponse
}

@Serializable
data class CoronaResponse(val totalCount: Int, val data: List<CoronaCenter>)

@Serializable
data class CoronaCenter(
    val address: String,
    val centerName: String,
    val centerType: String,
    val facilityName: String,
    val phoneNumber: String,
    val updatedAt: String,
    val lat: String,
    val lng: String
)
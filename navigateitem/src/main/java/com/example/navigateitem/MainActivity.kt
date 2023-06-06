package com.example.navigateitem

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigateitem.ui.theme.StudyComposeTheme
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val listAdapter: JsonAdapter<List<CustomItem>> by lazy {
        moshi.adapter(Types.newParameterizedType(List::class.java, CustomItem::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {

                val navHostController = rememberNavController()

                NavHost(navController = navHostController, startDestination = "screen1") {
                    composable("screen1") { entry ->

                        val text = entry.savedStateHandle.get<String>("input")

                        LazyColumn(modifier = Modifier.fillMaxSize()) {

                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                ) {
                                    Button(modifier = Modifier.align(Alignment.Center), onClick = {
                                        navHostController.navigate("screen2")
                                    }) {
                                        Text(text = "Route Screen2")
                                    }
                                }
                            }

                            text?.let {
                                items(listAdapter.fromJson(it)!!) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp)
                                    ) {
                                        Text(
                                            text = it.name,
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    composable("screen2") {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Button(modifier = Modifier.align(Alignment.Center), onClick = {
                                val dataList = listOf(
                                    CustomItem(1, "1번"),
                                    CustomItem(2, "2번"),
                                    CustomItem(3, "3번"),
                                    CustomItem(4, "4번"),
                                    CustomItem(5, "5번"),
                                    CustomItem(6, "6번"),
                                    CustomItem(7, "7번"),
                                    CustomItem(8, "8번"),
                                    CustomItem(9, "9번"),
                                    CustomItem(10, "10번"),
                                    CustomItem(11, "11번"),
                                    CustomItem(12, "12번"),
                                    CustomItem(13, "13번"),
                                    CustomItem(14, "14번"),
                                    CustomItem(15, "15번"),
                                    CustomItem(16, "16번"),
                                    CustomItem(17, "17번"),
                                    CustomItem(18, "18번"),
                                    CustomItem(19, "19번"),
                                    CustomItem(20, "20번"),
                                    CustomItem(21, "21번"),
                                    CustomItem(22, "22번"),
                                    CustomItem(23, "23번"),
                                    CustomItem(24, "24번"),
                                    CustomItem(25, "25번"),
                                    CustomItem(26, "26번"),
                                    CustomItem(27, "27번"),
                                    CustomItem(28, "28번"),
                                    CustomItem(29, "29번"),
                                    CustomItem(30, "30번"),
                                    CustomItem(31, "31번"),
                                    CustomItem(32, "32번"),
                                    CustomItem(33, "33번"),
                                    CustomItem(34, "34번"),
                                    CustomItem(35, "35번"),
                                    CustomItem(36, "36번"),
                                    CustomItem(37, "37번"),
                                    CustomItem(38, "38번"),
                                    CustomItem(39, "39번"),
                                    CustomItem(40, "40번"),
                                    CustomItem(41, "41번"),
                                    CustomItem(42, "42번"),
                                    CustomItem(43, "43번"),
                                    CustomItem(44, "44번"),
                                    CustomItem(45, "45번"),
                                    CustomItem(46, "46번"),
                                    CustomItem(47, "47번"),
                                    CustomItem(48, "48번"),
                                    CustomItem(49, "49번"),
                                    CustomItem(50, "50번"),
                                )

                                navHostController.previousBackStackEntry?.savedStateHandle?.set(
                                    "input",
                                    listAdapter.toJson(dataList)
                                )

                                navHostController.popBackStack()
                            }) {
                                Text(text = "Send Screen1")
                            }
                        }
                    }
                }
            }
        }
    }
}


@JsonClass(generateAdapter = true)
data class CustomItem(val id: Int, val name: String)


class CustomDataStore(private val context: Context) {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val listAdapter: JsonAdapter<List<CustomItem>> by lazy {
        moshi.adapter(Types.newParameterizedType(List::class.java, CustomItem::class.java))
    }

    private val Context.dataStore by preferencesDataStore(name = "list")

    private val customList =
        stringPreferencesKey("customList")

    val customListFlow: Flow<List<CustomItem>> =
        context.dataStore.data.map { preferences ->
            preferences[customList]?.let {
                listAdapter.fromJson(it) ?: emptyList()
            } ?: emptyList()
        }

    suspend fun setCustomList(list: List<CustomItem>) {
        context.dataStore.edit { mutablePreferences ->
            Log.d("결과", listAdapter.toJson(list))
            mutablePreferences[customList] = listAdapter.toJson(list)
        }
    }

}
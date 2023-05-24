package com.example.navigateitem

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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


    private val customDataStore by lazy { CustomDataStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyComposeTheme {
                // A surface container using the 'background' color from the theme


                val list = customDataStore.customListFlow.collectAsState(initial = emptyList())

                val scope = rememberCoroutineScope()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Button(onClick = {
                            Log.d("결과", list.value.toString())
                        }) {
                            Text(text = "Get")
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(onClick = {

                            CoroutineScope(Dispatchers.IO).launch {
                                val customList = mutableListOf<CustomItem>().apply {
                                    (1..10).forEach {
                                        add(CustomItem(it, "name$it"))
                                    }
                                }

                                customDataStore.setCustomList(emptyList())
                            }
                        }) {
                            Text(text = "Save")
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
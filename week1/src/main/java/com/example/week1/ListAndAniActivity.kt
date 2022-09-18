package com.example.week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class ListAndAniActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Conversation(list = personList)
        }

    }

    data class Person(val age: Int, val name: String, val content: String)

    companion object {

        private val arrayPerson = arrayOf("박", "덕", "성")
        private val arrayContent = arrayOf("안녕하세요", "반갑습니다", "환영합니다")

        val personList = mutableListOf<Person>().apply {
            for (i in 20..40) {
                add(Person(i, arrayPerson[i % 3], arrayContent[i % 3]))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConversation(){
    val arrayPerson = arrayOf("박", "덕", "성")
    val arrayContent = arrayOf("안녕하세요", "반갑습니다", "환영합니다")

    val personList = mutableListOf<ListAndAniActivity.Person>().apply {
        for (i in 20..40) {
            add(ListAndAniActivity.Person(i, arrayPerson[i % 3], arrayContent[i % 3]))
        }
    }
    Conversation(personList)
}


@Composable
fun Conversation(list: List<ListAndAniActivity.Person>) {
    LazyColumn {
        items(list) { item ->
            MessageCard(name = item.age.toString())
            MessageCard(name = item.name)
            MessageCard(name = item.content)
        }
    }
}


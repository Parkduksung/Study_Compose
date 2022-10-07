package com.example.week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun PreviewConversation() {
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
        itemsIndexed(list) { index, item ->
            when{
                index != 0 && index % 3 == 2 -> { Component3(item) }
                index != 0 && index % 3 == 1 -> { Component2(item) }
                else -> { Component1(item) }
            }
        }
    }
}


@Composable
fun Component1(item: ListAndAniActivity.Person) {
    Row(modifier = Modifier.background(Color.Blue)) {
        MessageCard(name = item.age.toString())
        MessageCard(name = item.name)
        MessageCard(name = item.content)
    }
}

@Composable
fun Component2(item: ListAndAniActivity.Person) {
    Row(modifier = Modifier.background(Color.Green)) {
        MessageCard(name = item.age.toString())
        MessageCard(name = item.name)
        MessageCard(name = item.content)
    }
}

@Composable
fun Component3(item: ListAndAniActivity.Person) {
    Row(modifier = Modifier.background(Color.Red)) {
        MessageCard(name = item.age.toString())
        MessageCard(name = item.name)
        MessageCard(name = item.content)
    }
}


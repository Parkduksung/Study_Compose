package com.example.timedistance

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val currentDateTime = LocalDateTime.now()
        val targetDateTime = LocalDateTime.parse("2023-06-19T13:00:00", formatter)
        val daysDifference = ChronoUnit.DAYS.between(targetDateTime.toLocalDate(),currentDateTime.toLocalDate())
        println(daysDifference)
    }
}
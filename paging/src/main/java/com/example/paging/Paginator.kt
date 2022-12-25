package com.example.paging

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}
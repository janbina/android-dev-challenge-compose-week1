package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.model.Goat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class Repository {

    private val goats = MutableStateFlow(createDummyGoats())

    fun getAllGoats(): Flow<List<Goat>> = goats

    fun getGoatById(id: Long): Flow<Goat?> = goats.map {
            goats -> goats.find { it.id == id }
    }

}

private fun createDummyGoats() = listOf<Goat>(
    Goat(
        1L,
        "Liza",
        unsplashUrl("1517411925961-8a5ea19bd27b")
    ),
    Goat(
        2L,
        "Roza",
        unsplashUrl("1593556050899-db53c20d061f")
    )
)

private fun unsplashUrl(photoId: String): String {
    return "\"https://images.unsplash.com/photo-$photoId?crop=entropy\\\\u0026cs=tinysrgb\\\\u0026fit=max\\\\u0026fm=jpg\\\\u0026ixlib=rb-1.2.1\\\\u0026q=80\\\\u0026w=1080\""
}
package com.example.androiddevchallenge.model

data class Goat(
    val id: Long,
    val name: String,
    val pictureUrl: String,
    val age: String,
    val owner: String,
    val info: String,
    val liked: Boolean = false,
)
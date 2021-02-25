/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.androiddevchallenge.model.Goat

class Repository {

    private val goats = MutableLiveData(createDummyGoats())

    fun getAllGoats(): LiveData<List<Goat>> = goats

    fun getGoatById(id: Long): LiveData<Goat?> = goats.map { goats -> goats.find { it.id == id } }

    fun setGoatLiked(id: Long, liked: Boolean) {
        val current = goats.value?.toMutableList() ?: return
        val index = current.indexOfFirst { it.id == id }
        if (index !in current.indices) return
        current[index] = current[index].copy(liked = liked)
        goats.value = current
    }
}

private fun createDummyGoats() = listOf<Goat>(
    Goat(
        1L,
        "Doris",
        unsplashUrl("1526065825811-91c653ee9aa6"),
        "2 months",
        "Ricky Graves",
        "If you want to keep a cow or a baby calf as a pet, there are a few things you need to know! A cow can live anywhere from 18-22 years in captivity when cared for throughout their life. This means that you will have your baby cow long after they grow big and become an adult cow! An adult cow can consume anywhere from 10-20 gallons of water a day. This is a lot of water! Make sure they always have access to fresh water so they can stay healthy and hydrated. Cows also like really salty foods! Although instead of feeding them salty foods, just make sure they have access to a salt lick. This can be purchased online or at a regular farm supply store. For regular day to day food, invest in a good cow feed. This can also be bought at a local farm supply store.",
    ),
    Goat(
        2L,
        "Lisa",
        unsplashUrl("1563906832557-b53ca701b251"),
        "1 year",
        "Felix Haynes",
        "A baby newborn goat is called a kid! Kind of like you! The mother goat is called a doe or a nanny while the father goat is called a billy or a buck. It’s not strange to find a goat with twin kids or even triplets! How would you feel if you had a twin brother or sister… or two! Goats that get together in a group are called a herd or a tribe. It is through this tribe that kids enjoy protection from the mother goat and the other older, more responsible goats. Goats are very vocal and like to call for each other when they need help or just want to play!\n" +
            "\n" +
            "After spending about 5 months in their mother’s womb, the mommy goat will give birth to the baby goat (kid). Just minutes after being born, goats are able to rise off the ground and try taking some baby steps. They’ll even begin to experiment with their voice and see just how vocal they can be. Like other mammals they like to drink their mother’s milk. They will drink from their mother until they are old enough to start tasting some yummy grasses.",
    ),
    Goat(
        3L,
        "Jasmine",
        unsplashUrl("1588466585717-f8041aec7875"),
        "4 years",
        "Ari Wise",
        "",
    ),
    Goat(
        4L,
        "Joey",
        unsplashUrl("1593556050899-db53c20d061f"),
        "6 years",
        "Yareli Richards",
        "",
    ),
    Goat(
        5L,
        "Toby",
        unsplashUrl("1517411925961-8a5ea19bd27b"),
        "2 years",
        "Mila Harper",
        "",
    )
)

private fun unsplashUrl(photoId: String): String {
    return "https://images.unsplash.com/photo-$photoId?crop=entropy\\u0026cs=tinysrgb\\u0026fit=max\\u0026fm=jpg\\u0026ixlib=rb-1.2.1\\u0026q=80\\u0026w=1080"
}

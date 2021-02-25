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
package com.example.androiddevchallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Repository
import com.example.androiddevchallenge.model.Goat
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun HomeScreen(
    repository: Repository,
    navigateToGoatAction: (Long) -> Unit,
) {
    val goats by repository.getAllGoats().observeAsState(emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        }
    ) {
        GoatList(
            modifier = Modifier.fillMaxWidth(),
            goats = goats,
            onGoatClick = { navigateToGoatAction(it.id) },
            onGoatLikeClicked = { repository.setGoatLiked(it.id, it.liked.not()) }
        )
    }
}

@Composable
fun GoatList(
    modifier: Modifier,
    goats: List<Goat>,
    onGoatClick: (Goat) -> Unit,
    onGoatLikeClicked: (Goat) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(goats) {
            GoatItem(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
                goat = it,
                onClick = { onGoatClick(it) },
                onLikeClick = { onGoatLikeClicked(it) },
            )
        }
    }
}

@Composable
fun GoatItem(
    modifier: Modifier,
    goat: Goat,
    onClick: () -> Unit,
    onLikeClick: () -> Unit,
) {
    Card(
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column {
            Box {
                CoilImage(
                    modifier = Modifier.fillMaxWidth().aspectRatio(16F / 9F),
                    data = goat.pictureUrl,
                    contentDescription = "Image of goat ${goat.name}",
                    fadeIn = true,
                    contentScale = ContentScale.Crop,
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = .5F)),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1F).padding(horizontal = 12.dp),
                        style = MaterialTheme.typography.h5,
                        text = goat.name,
                    )
                    IconButton(
                        modifier = Modifier,
                        onClick = onLikeClick
                    ) {
                        Icon(
                            imageVector = if (goat.liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Toggle liked state.",
                        )
                    }
                }
            }
        }
    }
}

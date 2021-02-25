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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Repository
import com.example.androiddevchallenge.model.Goat
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun DetailScreen(
    repository: Repository,
    goToHomeScreenAction: () -> Unit,
    goatId: Long,
) {
    val goat = repository.getGoatById(goatId).observeAsState().value ?: return

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = goToHomeScreenAction) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go back")
                    }
                },
                title = { Text(text = goat.name) },
                actions = {
                    IconButton(
                        modifier = Modifier,
                        onClick = { repository.setGoatLiked(goatId, goat.liked.not()) }
                    ) {
                        Icon(
                            imageVector = if (goat.liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Toggle liked state.",
                        )
                    }
                }
            )
        }
    ) {
        DetailContent(
            goat = goat,
        )
    }
}

@Composable
fun DetailContent(
    goat: Goat,
) {
    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Card {
            CoilImage(
                modifier = Modifier.fillMaxWidth().aspectRatio(16F / 9F),
                data = goat.pictureUrl,
                contentDescription = "Image of goat ${goat.name}",
                fadeIn = true,
                contentScale = ContentScale.Crop,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier,
            style = MaterialTheme.typography.body1,
            text = buildAnnotatedString {
                append(
                    AnnotatedString(
                        text = "Owner:",
                        spanStyle = SpanStyle(fontWeight = FontWeight.Medium)
                    )
                )
                append(" ")
                append(goat.owner)
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier,
            style = MaterialTheme.typography.body1,
            text = buildAnnotatedString {
                append(
                    AnnotatedString(
                        text = "Age:",
                        spanStyle = SpanStyle(fontWeight = FontWeight.Medium)
                    )
                )
                append(" ")
                append(goat.age)
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier,
            style = MaterialTheme.typography.body1,
            text = goat.info,
        )
    }
}

package com.mitch.ui_herolist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.mitch.core.ProgressBarState
import com.mitch.ui_herolist.components.HeroListItem
import com.mitch.ui_herolist.ui.HeroListState

@ExperimentalCoilApi
@Composable
fun HeroList(
    state: HeroListState,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(state.heroes) { hero ->
                HeroListItem(
                    hero = hero,
                    onSelectHero = { heroId ->
                        navigateToDetailScreen(heroId)
                    },
                    imageLoader = imageLoader,
                )
            }
        }
        if (state.progressBarState is ProgressBarState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
package com.mitch.ui_herolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mitch.core.ProgressBarState
import com.mitch.ui_herolist.components.HeroListItem

@Composable
fun HeroList(
    state: HeroListState
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(state.heroes) { hero ->
                HeroListItem(
                    hero = hero,
                    onSelectHero = {

                    }
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
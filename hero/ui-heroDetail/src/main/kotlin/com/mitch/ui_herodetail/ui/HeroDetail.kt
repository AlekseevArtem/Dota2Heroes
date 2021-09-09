package com.mitch.ui_herodetail.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HeroDetail(
    state: HeroDetailState,
) {
    state.hero?.let {
        Text(text = state.hero.localizedName)
    } ?: Text(text = "Loading...")
}
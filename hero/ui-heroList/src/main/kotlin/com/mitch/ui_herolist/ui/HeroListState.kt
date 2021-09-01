package com.mitch.ui_herolist.ui

import com.mitch.core.ProgressBarState
import com.mitch.hero_domain.Hero

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heroes: List<Hero> = listOf()
)

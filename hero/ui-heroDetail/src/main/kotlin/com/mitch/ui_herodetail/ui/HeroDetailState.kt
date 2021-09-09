package com.mitch.ui_herodetail.ui

import com.mitch.core.ProgressBarState
import com.mitch.hero_domain.Hero

data class HeroDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null,
)

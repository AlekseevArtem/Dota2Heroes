package com.mitch.ui_herodetail.ui

sealed class HeroDetailEvent {

    data class GetHeroesFromCache(
        val id: Int
    ) : HeroDetailEvent()
}

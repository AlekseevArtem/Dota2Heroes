package com.mitch.hero_interactors

import com.mitch.hero_datasource.network.HeroService

data class HeroInteractors(
    val getHeroes: GetHeroes,
    //TODO(Add other hero interactors)
) {

    companion object Factory {
        fun build(): HeroInteractors {
            val service = HeroService.build()
            return HeroInteractors(
                getHeroes = GetHeroes(
                    service = service
                )
            )
        }
    }
}
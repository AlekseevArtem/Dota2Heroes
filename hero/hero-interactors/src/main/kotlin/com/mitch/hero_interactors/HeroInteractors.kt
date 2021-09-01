package com.mitch.hero_interactors

import com.mitch.hero_datasource.cache.HeroCache
import com.mitch.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver

data class HeroInteractors(
    val getHeroes: GetHeroes,
    //TODO(Add other hero interactors)
) {

    companion object Factory {
        fun build(sqlDriver : SqlDriver): HeroInteractors {
            val service = HeroService.build()
            val cache = HeroCache.build(sqlDriver)
            return HeroInteractors(
                getHeroes = GetHeroes(
                    service = service,
                    cache = cache
                )
            )
        }
    }
}
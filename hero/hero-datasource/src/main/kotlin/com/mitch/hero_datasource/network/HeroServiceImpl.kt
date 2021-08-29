package com.mitch.hero_datasource.network

import com.mitch.hero_domain.Hero
import io.ktor.client.*
import io.ktor.client.request.*

class HeroServiceImpl(
    private val httpClient: HttpClient
) : HeroService {
    override suspend fun getHeroStats(): List<Hero> = httpClient
        .get<List<HeroDto>> {
            url(EndPoints.HERO_STATS)
        }.map { it.toHero() }

}
package com.mitch.hero_interactors

import com.mitch.core.DataState
import com.mitch.core.ProgressBarState
import com.mitch.core.UiComponent
import com.mitch.hero_datasource.cache.HeroCache
import com.mitch.hero_datasource.network.HeroService
import com.mitch.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetHeroes(
    private val service: HeroService,
    private val cache: HeroCache
) {
    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Loading))

            val heroes: List<Hero> = try {
                service.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Hero>>(
                        uiComponent = UiComponent.Dialog(
                            title = "Network data error",
                            description = e.message ?: "Unknown error"
                        )
                    )
                )
                listOf()
            }

            cache.insert(heroes)

            val cachedHeroes = cache.selectAll()

            emit(DataState.Data(data = cachedHeroes))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<List<Hero>>(
                    uiComponent = UiComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Idle))
        }
    }
}
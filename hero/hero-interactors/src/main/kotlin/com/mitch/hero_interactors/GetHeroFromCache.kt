package com.mitch.hero_interactors

import com.mitch.core.DataState
import com.mitch.core.ProgressBarState
import com.mitch.core.UiComponent
import com.mitch.hero_datasource.cache.HeroCache
import com.mitch.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetHeroFromCache(
    private val cache: HeroCache
) {
    fun execute(
        id: Int
    ): Flow<DataState<Hero>> = flow {
        try {
            emit(DataState.Loading<Hero>(progressBarState = ProgressBarState.Loading))

            val cachedHero = cache.getHero(id = id)
                ?: throw Exception("This hero does not exist in the cache.")

            emit(DataState.Data(data = cachedHero))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<Hero>(
                    uiComponent = UiComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading<Hero>(progressBarState = ProgressBarState.Idle))
        }
    }
}
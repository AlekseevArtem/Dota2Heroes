package com.mitch.hero_interactors

import com.mitch.core.DataState
import com.mitch.core.ProgressBarState
import com.mitch.core.UiComponent
import com.mitch.hero_datasource.network.HeroService
import com.mitch.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetHeroes(
    private val service: HeroService,
    // TODO(Add caching)
) {
    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val heroes: List<Hero> = try {
                service.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response(
                        uiComponent = UiComponent.Dialog(
                            title = "Network data error",
                            description = e.message ?: "Unknown error"
                        )
                    )
                )
                listOf()
            }

            //TODO(Caching)

            emit(DataState.Data(data = heroes))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response(
                    uiComponent = UiComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}
package com.mitch.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.core.DataState
import com.mitch.core.Logger
import com.mitch.core.UiComponent
import com.mitch.hero_interactors.GetHeroes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val getHeroes: GetHeroes,
) : ViewModel() {
    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())

    private val logger = Logger("HeroListViewModel")

    init {
        getHeroes()
    }

    private fun getHeroes() {
        getHeroes.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UiComponent.Dialog -> logger.log((dataState.uiComponent as UiComponent.Dialog).description)
                        is UiComponent.None -> logger.log((dataState.uiComponent as UiComponent.None).message)
                    }
                }
                is DataState.Data -> {
                    state.value = state.value.copy(heroes = dataState.data ?: listOf())
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
            }

        }.launchIn(viewModelScope)
    }

}
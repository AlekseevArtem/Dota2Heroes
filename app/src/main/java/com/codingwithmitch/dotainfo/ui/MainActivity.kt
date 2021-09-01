package com.codingwithmitch.dotainfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.util.DebugLogger
import com.codingwithmitch.dotainfo.R
import com.codingwithmitch.dotainfo.ui.theme.DotaInfoTheme
import com.mitch.core.DataState
import com.mitch.core.Logger
import com.mitch.core.ProgressBarState
import com.mitch.core.UiComponent
import com.mitch.hero_domain.Hero
import com.mitch.hero_interactors.HeroInteractors
import com.mitch.ui_herolist.HeroList
import com.mitch.ui_herolist.HeroListState
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val state: MutableState<HeroListState> = mutableStateOf(HeroListState())
    private lateinit var imageLoader : ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageLoader = ImageLoader.Builder(applicationContext)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()

        val getHeroes = HeroInteractors.build(
            sqlDriver = AndroidSqliteDriver(
                schema = HeroInteractors.schema,
                context = this,
                name = HeroInteractors.dbName
            )
        ).getHeroes
        val logger = Logger("GetHeroesTest")

        getHeroes.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UiComponent.Dialog -> logger.log((dataState.uiComponent as UiComponent.Dialog).description)
                        is UiComponent.None -> logger.log((dataState.uiComponent as UiComponent.None).message)
                    }
                }
                is DataState.Data -> {
                    state.value = state.value.copy(heroes = dataState.data?: listOf())
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
            }

        }.launchIn(CoroutineScope(IO))

        setContent {
            DotaInfoTheme {
                HeroList(
                    state = state.value,
                    imageLoader = imageLoader
                )
            }
        }
    }
}
















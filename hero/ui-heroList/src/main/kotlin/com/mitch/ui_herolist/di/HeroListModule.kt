package com.mitch.ui_herolist.di

import com.mitch.core.Logger
import com.mitch.hero_interactors.GetHeroes
import com.mitch.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    fun provideGetHeroes(
        interactors: HeroInteractors
    ): GetHeroes =
        interactors.getHeroes

    @Provides
    @Singleton
    @Named("heroListLogger")
    fun provideLogger(): Logger =
        Logger(
            tag = "HeroList",
            isDebug = true,
        )
}
package com.codingwithmitch.dotainfo.di

import android.app.Application
import com.mitch.hero_interactors.HeroInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroInteractorsModule {

    @Provides
    @Singleton
    @Named("HeroAndroidSqlDriver")
    fun provideAndroidDriver(
        app: Application
    ): SqlDriver =
        AndroidSqliteDriver(
            schema = HeroInteractors.schema,
            context = app,
            name = HeroInteractors.dbName
        )

    @Provides
    @Singleton
    fun provideHeroInteractors(
        @Named("HeroAndroidSqlDriver") sqlDriver: SqlDriver
    ): HeroInteractors =
        HeroInteractors.build(sqlDriver)
}
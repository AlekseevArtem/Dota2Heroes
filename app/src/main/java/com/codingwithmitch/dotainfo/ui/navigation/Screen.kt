package com.codingwithmitch.dotainfo.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
){

    object HeroList: Screen(
        route = "heroList",
        arguments = emptyList()
    )

    object HeroDetail: Screen(
        route = "HeroDetail",
        arguments = listOf(
            navArgument(name = "heroId") {
                type = NavType.IntType
            }
        )
    )
}

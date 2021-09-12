package com.mitch.ui_herodetail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.mitch.hero_domain.Hero
import com.mitch.hero_domain.maxAttackDmg
import com.mitch.hero_domain.minAttackDmg
import com.mitch.ui_herodetail.R
import kotlin.math.round

@ExperimentalCoilApi
@Composable
fun HeroDetail(
    state: HeroDetailState,
    imageLoader: ImageLoader,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        state.hero?.let { hero ->
            item {
                val painter = rememberImagePainter(
                    data = hero.img,
                    imageLoader = imageLoader,
                    builder = {
                        placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                    }
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 200.dp),
                    painter = painter,
                    contentDescription = hero.localizedName,
                    contentScale = ContentScale.Crop,
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = hero.localizedName,
                            style = MaterialTheme.typography.h1
                        )
                        val iconPainter = rememberImagePainter(
                            data = state.hero.icon,
                            imageLoader = imageLoader,
                            builder = {
                                placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                            },
                        )
                        Image(
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp),
                            painter = iconPainter,
                            contentDescription = hero.localizedName,
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(bottom = 4.dp),
                        text = hero.primaryAttribute.uiValue,
                        style = MaterialTheme.typography.subtitle1,
                    )
                    Text(
                        modifier = Modifier
                            .padding(bottom = 12.dp),
                        text = hero.attackType.uiValue,
                        style = MaterialTheme.typography.caption
                    )

                    HeroBaseStats(
                        hero = hero,
                        padding = 10.dp,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    WinPercentages(hero = hero)
                }
            }
        }
    }
}

@Composable
fun WinPercentages(
    hero: Hero,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val proWinPercentage =
                remember { round(hero.proWins.toDouble() / hero.proPick.toDouble() * 100).toInt() }
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Pro Wins",
                style = MaterialTheme.typography.h2,
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "$proWinPercentage %",
                style = MaterialTheme.typography.h2,
                color = if (proWinPercentage > 50) Color(0xFF009a34) else MaterialTheme.colors.error

            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val turboWinPercentage =
                remember { round(hero.turboWins.toDouble() / hero.turboPicks.toDouble() * 100).toInt() }
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Turbo Wins",
                style = MaterialTheme.typography.h2,
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "$turboWinPercentage %",
                style = MaterialTheme.typography.h2,
                color = if (turboWinPercentage > 50) Color(0xFF009a34) else MaterialTheme.colors.error
            )
        }
    }
}

@Composable
fun HeroBaseStats(
    hero: Hero,
    padding: Dp,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = "Base Stats",
                style = MaterialTheme.typography.h4
            )
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = 20.dp)
                ) {
                    HolderForStat(
                        name = stringResource(R.string.strength),
                        firstValue = hero.baseStr.toString(),
                        between = "+",
                        secondValue = hero.strGain.toString()
                    )

                    HolderForStat(
                        name = stringResource(R.string.agility),
                        firstValue = hero.baseAgi.toString(),
                        between = "+",
                        secondValue = hero.agiGain.toString()
                    )

                    HolderForStat(
                        name = stringResource(R.string.intelligence),
                        firstValue = hero.baseInt.toString(),
                        between = "+",
                        secondValue = hero.intGain.toString()
                    )

                    val health = remember { round(hero.baseHealth + hero.baseStr * 20).toInt() }
                    HolderForStat(
                        name = stringResource(R.string.health),
                        firstValue = health.toString(),
                    )

                }
                Column {

                    HolderForStat(
                        name = stringResource(R.string.attack_range),
                        firstValue = hero.attackRange.toString(),
                    )

                    HolderForStat(
                        name = stringResource(R.string.projectile_speed),
                        firstValue = hero.projectileSpeed.toString(),
                    )

                    HolderForStat(
                        name = stringResource(R.string.move_speed),
                        firstValue = hero.moveSpeed.toString(),
                    )

                    HolderForStat(
                        name = stringResource(R.string.attack_dmg),
                        firstValue = "${hero.minAttackDmg()} - ${hero.maxAttackDmg()}",
                    )

                }
            }
        }
    }
}

@Composable
fun HolderForStat(
    name: String,
    firstValue: String,
    between: String? = null,
    secondValue: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$name:",
            style = MaterialTheme.typography.body2,
        )

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = firstValue,
                style = MaterialTheme.typography.body2,
            )
            if (between != null && secondValue != null) {
                Text(
                    text = " $between ",
                    style = MaterialTheme.typography.caption,
                )
                Text(
                    text = secondValue,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

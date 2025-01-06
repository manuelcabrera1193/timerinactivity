package com.willard.cabrera.timerinactivity.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun getRandomComposeColor(): Color {
    val red = Random.nextFloat()
    val green = Random.nextFloat()
    val blue = Random.nextFloat()
    return Color(red, green, blue, alpha = 1.0f)
}
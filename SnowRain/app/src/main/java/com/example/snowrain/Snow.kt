package com.example.snowrain

import java.util.Random

data class Snow(
    val viewWidth: Int,
    val viewHeight: Int,
    var x: Int = Random().nextInt(viewWidth),
    var y: Int = Random().nextInt(viewHeight),
    var scale: Float = Random().nextFloat() % 0.1f,
) {
    fun random(){
        x = Random().nextInt(viewWidth)
        y = 0
        scale = Random().nextFloat() % 0.1f
    }
}
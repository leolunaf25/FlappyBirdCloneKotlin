package com.lunatcoms.flappybirdclone

import android.graphics.Bitmap

class Background(var image: Bitmap, var x: Int, var y: Int) {
    private val speed = 5  // Velocidad a la que se moverá el fondo

    fun update() {
        x -= speed  // Mover el fondo hacia la izquierda
    }
}

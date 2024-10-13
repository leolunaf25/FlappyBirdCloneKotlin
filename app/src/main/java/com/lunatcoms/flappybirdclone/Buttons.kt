package com.lunatcoms.flappybirdclone


import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.View
import android.view.View.OnClickListener


class Buttons(
    val image: Bitmap, // Imagen del personaje
    var x: Float, // Posición X del personaje
    var y: Float, // Posición Y del personaje (posición vertical)
) : OnClickListener {

    // Método que actualiza la posición del personaje
    fun update() {


    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x, y, null)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}
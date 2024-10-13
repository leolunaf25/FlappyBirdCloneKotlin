package com.lunatcoms.flappybirdclone

import android.graphics.Bitmap
import android.graphics.Canvas

// Clase que representa al personaje del juego
class Character(
    val image: Bitmap, // Imagen del personaje
    var x: Float, // Posición X del personaje
    var y: Float, // Posición Y del personaje (posición vertical)
    var groundLevel: Float,
    var rooftLevel: Float
) {
    var velocityY = 0 // Velocidad vertical del personaje
    private val gravity = 3 // Constante de gravedad (puedes ajustarla)
    var jumpForce = -40 // Fuerza del salto (puedes ajustarla)
    //private val groundLevel: Int = y // Nivel del suelo (posición Y máxima)

    // Método que actualiza la posición del personaje
    fun update() {
        // Aplicar la gravedad
        velocityY += gravity
        y += velocityY

        // Evitar que el personaje caiga más allá del suelo
        if (y > groundLevel) {
            y = groundLevel // Fijar el personaje en el suelo
            velocityY = 0 // Detener la velocidad al tocar el suelo
        }

        if (y < rooftLevel){
            y = rooftLevel
            velocityY = 0
        }

    }

    fun jump() {
        velocityY = jumpForce // Aplicar impulso hacia arriba cada vez que se toque la pantalla
    }
    /*
    // Método que maneja el salto del personaje
    fun jump() {
        // Solo permite saltar si el personaje está en el suelo
        if (y == groundLevel) {
            velocityY = jumpForce // Aplicar la fuerza del salto
        }
    }*/

    // Método para dibujar el personaje en el Canvas
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x, y, null)
    }
}

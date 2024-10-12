package com.lunatcoms.flappybirdclone

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

// Clase que representa un obstáculo usando imágenes
class Obstacle(
    private val screenWidth: Float,
    private val screenHeight: Int,
    private val gapHeight: Int, // Altura del hueco
    private var xPosition: Float, // Posición X del obstáculo
    private val topObstacleImage: Bitmap, // Imagen del obstáculo superior
    private val bottomObstacleImage: Bitmap // Imagen del obstáculo inferior
) {
    private val topObstacleHeight: Int = (Math.random() * (screenHeight - gapHeight)).toInt()
    private val bottomObstacleHeight: Int = screenHeight - topObstacleHeight - gapHeight

    // Rectángulos que indican las posiciones de los obstáculos
    val topRect = Rect(xPosition.toInt(), 0, xPosition.toInt() + topObstacleImage.width, topObstacleHeight)
    val bottomRect = Rect(xPosition.toInt(), screenHeight - bottomObstacleHeight, xPosition.toInt() + bottomObstacleImage.width, screenHeight)

    // Método para mover los obstáculos hacia la izquierda
    fun update() {
        xPosition -= 10 // Velocidad del obstáculo (ajustable)
        topRect.offset(-10, 0)
        bottomRect.offset(-10, 0)

        // Si el obstáculo sale de la pantalla, lo reposicionamos
        if (xPosition + topObstacleImage.width < -100) {
            xPosition = screenWidth
            val newTopHeight = (Math.random() * (screenHeight - gapHeight)).toInt()
            val newBottomHeight = screenHeight - newTopHeight - gapHeight
            topRect.set(xPosition.toInt(), 0, xPosition.toInt() + topObstacleImage.width, newTopHeight)
            bottomRect.set(xPosition.toInt(), screenHeight - newBottomHeight, xPosition.toInt() + bottomObstacleImage.width, screenHeight)
        }
    }

    // Método para dibujar los obstáculos en el canvas usando imágenes
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(topObstacleImage, null, topRect, null)
        canvas.drawBitmap(bottomObstacleImage, null, bottomRect, null)
    }

    // Método para verificar si el personaje colisiona con los obstáculos
    fun checkCollision(character: Character): Boolean {
        val characterRect = Rect(character.x.toInt(), character.y.toInt(), (character.x + character.image.width).toInt(), (character.y + character.image.height).toInt())
        return Rect.intersects(characterRect, topRect) || Rect.intersects(characterRect, bottomRect)
    }
}

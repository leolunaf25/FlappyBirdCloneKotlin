package com.lunatcoms.flappybirdclone

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import kotlin.random.Random


// Clase que representa un obstáculo usando imágenes
class Obstacle(
    private val screenWidth: Float,
    private val screenHeight: Int,
    private var gapHeight: Int, // Tamaño del hueco
    private var xPosition: Float,  // Posición X del obstáculo
    private val topObstacleImage: Bitmap, // Imagen del obstáculo superior (redimensionada)
    private val bottomObstacleImage: Bitmap, // Imagen del obstáculo inferior (redimensionada)
    private val gapImage: Bitmap
) {
    private val obstacleWidth: Int = topObstacleImage.width
    private val obstacleHeight: Int = topObstacleImage.height

    private var topObstacleY: Int // Posición Y del obstáculo superior
    private var bottomObstacleY: Int // Posición Y del obstáculo inferior
    private var gapY: Int
    var hasBeenPassed: Boolean = false
    private var scaledgap: Bitmap = Bitmap.createScaledBitmap(
        gapImage,
        gapImage.width,
        gapHeight,
        false
    )

    init {
        // Generar una posición aleatoria para el obstáculo superior
        //topObstacleY = (Math.random() * (screenHeight - gapHeight - obstacleHeight)).toInt()
        topObstacleY = Random.nextInt(- obstacleHeight + (screenHeight-obstacleHeight-gapHeight),0)
        bottomObstacleY = topObstacleY + obstacleHeight + gapHeight
        gapY = topObstacleY + obstacleHeight
    }

    // Método para mover los obstáculos
    fun update() {
        gapHeight = Random.nextInt(screenHeight / 5, screenHeight / 4)

        xPosition -= 10 // Velocidad del obstáculo (ajustable)

        // Reposicionar el obstáculo si sale de la pantalla
        if (xPosition + obstacleWidth < 0) {
            xPosition = screenWidth
            // Generar una nueva posición Y para el obstáculo superior
            //topObstacleY = (Math.random() * (screenHeight - gapHeight - obstacleHeight)).toInt()
            //topObstacleY = - obstacleHeight + (screenHeight-obstacleHeight)
            topObstacleY = Random.nextInt(- obstacleHeight + (screenHeight-obstacleHeight-gapHeight),0)
            bottomObstacleY = topObstacleY + obstacleHeight + gapHeight
            gapY = topObstacleY + obstacleHeight

            scaledgap = Bitmap.createScaledBitmap(gapImage, gapImage.width, gapHeight, false)
            hasBeenPassed = false
        }
    }

    // Método para dibujar los obstáculos en el canvas
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(topObstacleImage, xPosition, topObstacleY.toFloat(), null)
        canvas.drawBitmap(scaledgap, xPosition, gapY.toFloat(), null)
        canvas.drawBitmap(bottomObstacleImage, xPosition, bottomObstacleY.toFloat(), null)
    }

    // Método para verificar colisiones
    fun checkCollision(character: Character): Boolean {
        val characterRect = Rect(character.x.toInt(), character.y.toInt(), character.x.toInt() + character.image.width, character.y.toInt() + character.image.height)
        val topRect = Rect(xPosition.toInt(), topObstacleY, xPosition.toInt() + obstacleWidth, topObstacleY + obstacleHeight)
        val bottomRect = Rect(xPosition.toInt(), bottomObstacleY, xPosition.toInt() + obstacleWidth, bottomObstacleY + obstacleHeight)
        return Rect.intersects(characterRect, topRect) || Rect.intersects(characterRect, bottomRect)
    }

    fun chekPoint(character: Character): Boolean {
        return character.x > xPosition
    }

    fun markAsPassed(){
        hasBeenPassed = true
    }
}


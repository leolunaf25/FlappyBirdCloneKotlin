package com.lunatcoms.flappybirdclone

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import kotlin.random.Random

// Modificamos la clase GameView para incluir el personaje y el control de salto
class GameView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), Runnable {

    private var thread: Thread? = null
    private var isPlaying = false
    private var isGameOver = false

    private var background1: Background
    private var background2: Background

    private var character: Character
    private var obstacles: MutableList<Obstacle> = mutableListOf()

    init {
        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        // Configuración del fondo y el personaje
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.background_game)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, screenHeight, false)

        background1 = Background(scaledBitmap, 0, 0)
        background2 = Background(scaledBitmap, background1.image.width, 0)

        val characterBitmap = BitmapFactory.decodeResource(resources, R.drawable.bird_main)
        val scaledCharacter =
            Bitmap.createScaledBitmap(characterBitmap, screenWidth / 8, screenHeight / 16, false)
        character = Character(
            scaledCharacter,
            screenWidth / 3F,
            screenHeight / 3F,
            screenHeight - screenHeight / 16F,
            0F
        )

        // Inicialización de obstáculos (igual que antes)
        val topObstacleImage = BitmapFactory.decodeResource(resources, R.drawable.top_pipe)
        val bottomObstacleImage = BitmapFactory.decodeResource(resources, R.drawable.bottom_pipe)

        val gapHeight = Random.nextInt(screenHeight / 5, screenHeight / 3)
        val gap = BitmapFactory.decodeResource(resources, R.drawable.gap)

        val scaledTopObstacle = Bitmap.createScaledBitmap(
            topObstacleImage,
            topObstacleImage.width,
            (screenHeight * .6).toInt(),
            false
        )
        val scaledBottomObstacle = Bitmap.createScaledBitmap(
            bottomObstacleImage,
            bottomObstacleImage.width,
            (screenHeight * .6).toInt(),
            false
        )

        obstacles.add(
            Obstacle(
                screenWidth.toFloat(),
                screenHeight,
                gapHeight,
                screenWidth.toFloat(),
                scaledTopObstacle,
                scaledBottomObstacle,
                gap
            )
        )

        obstacles.add(
            Obstacle(
                screenWidth.toFloat(),
                screenHeight,
                gapHeight,
                screenWidth + (screenWidth * 0.55F),
                scaledTopObstacle,
                scaledBottomObstacle,
                gap
            )
        )
    }

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            sleep()
        }
    }

    private fun update() {
        if (isGameOver) {
            character.update() // Solo actualizamos el personaje en caída
            return
        }

        background1.update()
        background2.update()

        if (background1.x + background1.image.width <= 0) {
            background1.x = background2.x + background2.image.width
        }
        if (background2.x + background2.image.width <= 0) {
            background2.x = background1.x + background1.image.width
        }

        character.update()
        for (obstacle in obstacles) {
            obstacle.update()

            if (obstacle.chekPoint(character) && !obstacle.hasBeenPassed){
                (context as MainActivity).highScore()
                obstacle.markAsPassed()
            }

            if (obstacle.checkCollision(character)) {
                isGameOver = true
                character.velocityY = 0 // El personaje deja de saltar
                character.jumpForce = 0

                // Llamar a la función de la Activity para mostrar los botones
                (context as MainActivity).showGameOverButtons()
                break
            }

        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            val canvas: Canvas = holder.lockCanvas()

            canvas.drawBitmap(background1.image, background1.x.toFloat(), background1.y.toFloat(), null)
            canvas.drawBitmap(background2.image, background2.x.toFloat(), background2.y.toFloat(), null)

            for (obstacle in obstacles) {
                obstacle.draw(canvas)
            }

            character.draw(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun sleep() {
        Thread.sleep(17)
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this)
        thread?.start()
    }

    fun pause() {
        isPlaying = false
        try {
            thread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            if (!isGameOver) {
                character.jump()
            }
        }
        return true
    }
}


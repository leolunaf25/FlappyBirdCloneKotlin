package com.lunatcoms.flappybirdclone

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView

// Modificamos la clase GameView para incluir el personaje y el control de salto
class GameView(context: Context) : SurfaceView(context), Runnable {

    private var thread: Thread? = null
    private var isPlaying = false
    private var isGameOver = false // Indica si el juego terminó


    private var background1: Background
    private var background2: Background

    // Instancia del personaje
    private var character: Character
    private var obstacles: MutableList<Obstacle> = mutableListOf() // Lista de obstáculos


    init {
        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        // Cargar el fondo y ajustarlo al tamaño de la pantalla
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.background_game2)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, false)

        background1 = Background(scaledBitmap, 0, 0)
        background2 = Background(scaledBitmap, screenWidth, 0)

        // Cargar el personaje y posicionarlo en la mitad de la pantalla
        val characterBitmap = BitmapFactory.decodeResource(resources, R.drawable.bird_main)
        val scaledCharacter =
            Bitmap.createScaledBitmap(characterBitmap, screenWidth / 8, screenHeight / 16, false)

        // Inicializamos al personaje en el centro horizontal y cerca del suelo
        character = Character(
            scaledCharacter,
            screenWidth / 3F,
            screenHeight / 3F,
            screenHeight - screenHeight / 16F,
             0F
        )

        // Cargar imágenes de obstáculos
        val topObstacleImage = BitmapFactory.decodeResource(resources, R.drawable.top_pipe)
        val bottomObstacleImage = BitmapFactory.decodeResource(resources, R.drawable.bottom_pipe)

        // Crear obstáculos iniciales
        val gapHeight = screenHeight / 4
        obstacles.add(Obstacle(screenWidth.toFloat(), screenHeight, gapHeight, screenWidth.toFloat(), topObstacleImage, bottomObstacleImage))
        obstacles.add(Obstacle(screenWidth.toFloat(), screenHeight, gapHeight, screenWidth + (screenWidth*0.65F), topObstacleImage, bottomObstacleImage))
    }

    override fun run() {
        while (isPlaying) {
            update() // Actualizamos la posición del fondo y del personaje
            draw() // Dibujamos el fondo y el personaje en la pantalla
            sleep() // Controlamos el framerate
        }
    }

    private fun update() {
        if (isGameOver) {
            return // Si es Game Over, no actualizar más
        }

        // Actualizar el fondo
        background1.update()
        background2.update()

        if (background1.x + background1.image.width <= 0) {
            background1.x = background2.x + background2.image.width
        }

        if (background2.x + background2.image.width <= 0) {
            background2.x = background1.x + background1.image.width
        }

        // Actualizar el personaje y los obstáculos
        character.update()
        for (obstacle in obstacles) {
            obstacle.update()

            // Verificar colisiones
            if (obstacle.checkCollision(character)) {
                //isGameOver = true
                character.velocityY = 0 // El personaje deja de saltar
                //break
            }
        }
    }
    private fun draw() {
        if (holder.surface.isValid) {
            val canvas: Canvas = holder.lockCanvas()

            // Dibujar los fondos
            canvas.drawBitmap(background1.image, background1.x.toFloat(), background1.y.toFloat(), null)
            canvas.drawBitmap(background2.image, background2.x.toFloat(), background2.y.toFloat(), null)

            // Dibujar los obstáculos
            for (obstacle in obstacles) {
                obstacle.draw(canvas)
            }

            // Dibujar el personaje
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

    // Detectar toques en la pantalla para hacer que el personaje salte
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            character.jump() // Hacer que el personaje salte al tocar la pantalla
        }
        return true
    }
}

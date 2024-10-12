package com.lunatcoms.flappybirdclone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameView = GameView(this)
        setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()  // Iniciar o reanudar el juego
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()  // Pausar el juego cuando la app no esté activa
    }
}

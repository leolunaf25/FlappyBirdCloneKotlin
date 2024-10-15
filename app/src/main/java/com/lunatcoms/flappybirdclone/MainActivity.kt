package com.lunatcoms.flappybirdclone

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameView = findViewById<GameView>(R.id.gameView)

        // Obtener los botones
        val restartButton = findViewById<ImageView>(R.id.btnRestart)
        val exitButton = findViewById<ImageView>(R.id.btnExit)

        // Configurar los listeners de los botones
        restartButton.setOnClickListener {
            Toast.makeText(this, "Reiniciar", Toast.LENGTH_SHORT).show()
            // Aquí puedes implementar la lógica para reiniciar el juego
        }

        exitButton.setOnClickListener {
            Toast.makeText(this, "Salir", Toast.LENGTH_SHORT).show()
            // Aquí puedes implementar la lógica para salir del juego
        }
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()  // Iniciar o reanudar el juego
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()  // Pausar el juego cuando la app no esté activa
    }

    fun showGameOverButtons() {
        runOnUiThread {
            val restartButton = findViewById<ImageView>(R.id.btnRestart)
            val exitButton = findViewById<ImageView>(R.id.btnExit)

            restartButton.visibility = View.VISIBLE
            exitButton.visibility = View.VISIBLE
        }
    }

}
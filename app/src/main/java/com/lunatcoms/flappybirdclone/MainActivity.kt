package com.lunatcoms.flappybirdclone

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView
    private var scorePoints: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameView = findViewById<GameView>(R.id.gameView)


        // Obtener los botones
        val restartButton = findViewById<ImageView>(R.id.btnRestart)
        val exitButton = findViewById<ImageView>(R.id.btnExit)

        val maxScore = findViewById<TextView>(R.id.tvMaxScore)
        maxScore.text = getHighScore().toString()

        // Configurar los listeners de los botones
        restartButton.setOnClickListener {
            // Aquí puedes implementar la lógica para reiniciar el juego
            gameView.pause()
            recreate()
        }

        exitButton.setOnClickListener {
            Toast.makeText(this, "Salir", Toast.LENGTH_SHORT).show()
            // Aquí puedes implementar la lógica para salir del juego
            finishAffinity()
            exitProcess(0)
            //finish()  // Cierra la actividad actual y regresa a la anterior

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

    fun highScore(){
        var score = findViewById<TextView>(R.id.tvScore)
        scorePoints++

        runOnUiThread {
            score.text = scorePoints.toString()
        }

        if (scorePoints > getHighScore()) {
            saveHighScore(scorePoints)
        }

    }

    fun saveHighScore(score: Int) {
        val sharedPreferences = getSharedPreferences("game_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("high_score", score)
        editor.apply()
    }

    fun getHighScore(): Int {
        val sharedPreferences = getSharedPreferences("game_prefs", MODE_PRIVATE)
        return sharedPreferences.getInt("high_score", 0)
    }




}
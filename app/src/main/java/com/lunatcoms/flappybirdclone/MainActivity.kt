package com.lunatcoms.flappybirdclone

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.lunatcoms.flappybirdclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView
    private var scorePoints: Int = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        }

        gameView = findViewById<GameView>(R.id.gameView)

        // Obtener los botones
        val restartButton = findViewById<ImageView>(R.id.btnRestart)
        val exitButton = findViewById<ImageView>(R.id.btnExit)

        // Configurar los listeners de los botones
        restartButton.setOnClickListener {
            // Aquí puedes implementar la lógica para reiniciar el juego
            gameView.pause()
            recreate()
        }

        exitButton.setOnClickListener {
            // Aquí puedes implementar la lógica para salir del juego
            //finishAffinity()
            //exitProcess(0)
            finish()  // Cierra la actividad actual y regresa a la anterior

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
        updatePoints()
        runOnUiThread {
            binding.cvBoard.visibility = View.VISIBLE
        }
    }

    fun highScore() {
        var score = findViewById<TextView>(R.id.tvScore)
        scorePoints++

        runOnUiThread {
            score.text = scorePoints.toString()
        }

        if (scorePoints > getHighScore()) {
            saveHighScore(scorePoints)
        }
    }

    private fun updatePoints() {
        runOnUiThread {
            binding.tvRecord.text = getHighScore().toString()
            binding.tvScoreBoard.text = scorePoints.toString()
        }
    }

    private fun saveHighScore(score: Int) {
        val sharedPreferences = getSharedPreferences("game_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("high_score", score)
        editor.apply()
    }

    private fun getHighScore(): Int {
        val sharedPreferences = getSharedPreferences("game_prefs", MODE_PRIVATE)
        return sharedPreferences.getInt("high_score", 0)
    }

}
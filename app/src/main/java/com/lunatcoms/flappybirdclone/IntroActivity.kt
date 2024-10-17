package com.lunatcoms.flappybirdclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lunatcoms.flappybirdclone.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivPlay.setOnClickListener {
            navigateToGame()
        }
    }

    private fun navigateToGame() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
package com.example.a25a_hw1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class TitleScreenActivity : AppCompatActivity() {


    private lateinit var title_BTN_start : MaterialButton
    private lateinit var title_BTN_settings : MaterialButton
    private lateinit var title_BTN_exit : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_title_screen)

        findViews()
        initViews()
    }

    private fun findViews() {
        title_BTN_start = findViewById(R.id.title_BTN_start)
        title_BTN_settings = findViewById(R.id.title_BTN_settings)
        title_BTN_exit = findViewById(R.id.title_BTN_exit)
    }

    private fun initViews() {
        title_BTN_start.setOnClickListener({v -> startGame()})
        title_BTN_settings.setOnClickListener({v -> enterSettings()})
        title_BTN_exit.setOnClickListener({v -> finish()})
    }

    private fun enterSettings() {
        Toast.makeText(this, "Settings not implemented yet", Toast.LENGTH_SHORT).show()
    }

    private fun startGame() {
        val intent = Intent(this@TitleScreenActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
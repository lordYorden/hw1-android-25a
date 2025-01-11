package com.example.a25a_hw1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class TitleScreenActivity : AppCompatActivity() {


    private lateinit var title_BTN_start: MaterialButton
    private lateinit var title_BTN_settings: MaterialButton
    private lateinit var title_BTN_exit: MaterialButton
    private lateinit var title_BTN_scoreboard: MaterialButton

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
        title_BTN_scoreboard = findViewById(R.id.title_BTN_scoreboard)
    }

    private fun initViews() {
        title_BTN_start.setOnClickListener { _ ->
            startGame()
        }
        title_BTN_settings.setOnClickListener { _ ->
            enterSettings()
        }
        title_BTN_exit.setOnClickListener { _ ->
            finish()
        }
        title_BTN_scoreboard.setOnClickListener { _ ->
            enterScoreboard()
        }
    }

    private fun enterScoreboard() {
        val intent = Intent(this@TitleScreenActivity, GameOverActivity::class.java)
        startActivity(intent)
    }

    private fun enterSettings() {
        //Toast.makeText(this, "Settings not implemented yet", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@TitleScreenActivity, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun startGame() {
        val intent = Intent(this@TitleScreenActivity, MainActivity::class.java)
        startActivity(intent)
        //finish()
    }
}
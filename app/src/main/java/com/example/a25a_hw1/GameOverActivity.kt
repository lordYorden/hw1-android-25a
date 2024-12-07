package com.example.a25a_hw1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class GameOverActivity : AppCompatActivity() {


    private lateinit var GameOver_LBL_score: MaterialTextView
    private lateinit var GameOver_BTN_restart: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        findViews()
        initViews()
    }

    private fun initViews() {
        val bundle: Bundle? = intent.extras

        val score = bundle?.getInt("SCORE_KEY", 0)
        val status = bundle?.getString("STATUS_KEY", "Game Over!\nYour Score was:")

        GameOver_LBL_score.text = buildString {
            append(status)
            append("\n")
            append(score)
        }

        GameOver_BTN_restart.setOnClickListener {
            moveBack()
        }

    }

    private fun moveBack() {
        val intent = Intent(this@GameOverActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun findViews() {
        GameOver_LBL_score = findViewById(R.id.GameOver_LBL_score)
        GameOver_BTN_restart = findViewById(R.id.GameOver_BTN_restart)
    }
}
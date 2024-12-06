package com.example.a25a_hw1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a25a_l02_03.logic.GameManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var main_FAB_right : FloatingActionButton
    private lateinit var main_FAB_left : FloatingActionButton
    private lateinit var main_IMG_cowboys: Array<AppCompatImageView>
    private lateinit var main_IMG_hearts : Array<AppCompatImageView>

    private lateinit var gameManager: GameManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        gameManager = GameManager(main_IMG_hearts.size, main_IMG_cowboys.size)
        initViews()
    }

    private fun initViews() {
        main_FAB_right.setOnClickListener({v -> moveRight()})
        main_FAB_left.setOnClickListener({v -> moveLeft()})
        updateUI()
    }

    private fun moveLeft() {
        gameManager.moveLeft()
        updateUI()
    }

    private fun moveRight() {
        gameManager.moveRight()
        updateUI()
    }

    private fun updateUI() {
        //Toast.makeText(this, "${gameManager.cowboyIndex}", Toast.LENGTH_SHORT).show()
        if (gameManager.timesHit != 0) {
            main_IMG_hearts[main_IMG_hearts.size - gameManager.timesHit].visibility = View.INVISIBLE
        }

        for (i in main_IMG_cowboys.indices) {
            if (i == gameManager.cowboyIndex) {
                main_IMG_cowboys[i].visibility = View.VISIBLE
            } else {
                main_IMG_cowboys[i].visibility = View.INVISIBLE
            }
        }
    }

    private fun findViews() {
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )

        main_IMG_cowboys = arrayOf(
            findViewById(R.id.main_IMG_cowboy1),
            findViewById(R.id.main_IMG_cowboy2),
            findViewById(R.id.main_IMG_cowboy3)
        )

        main_FAB_right = findViewById(R.id.main_FAB_right)
        main_FAB_left = findViewById(R.id.main_FAB_left)
    }
}
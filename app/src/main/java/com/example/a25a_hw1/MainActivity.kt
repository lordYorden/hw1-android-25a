package com.example.a25a_hw1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.lifecycleScope
import com.example.a25a_hw1.logic.GameManager
import com.example.a25a_hw1.logic.SettingsManager
import com.example.a25a_hw1.utilities.BackgroundMusicPlayer
import com.example.a25a_hw1.utilities.Constants
import com.example.a25a_hw1.utilities.SignalManager
import com.example.a25a_hw1.utilities.SingleSoundPlayer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var main_FAB_right : FloatingActionButton
    private lateinit var main_FAB_left : FloatingActionButton
    private lateinit var main_IMG_cowboys: Array<AppCompatImageView>
    private lateinit var main_IMG_hearts : Array<AppCompatImageView>
    private lateinit var main_LBL_score: MaterialTextView
    private lateinit var main_IMG_tumbleweeds : Array<Array<AppCompatImageView>>
    private lateinit var gameManager: GameManager
    private var numRows: Int = 0


    //val handler: Handler = Handler(Looper.getMainLooper())
    private var timerOn: Boolean = false
    private lateinit var timerJob: Job

//    private val runnable: Runnable =  object : Runnable{
//        override fun run() {
//            startTimer()
//            gameManager.advanceTumbleweeds()
//            updateUI()
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        numRows = main_IMG_tumbleweeds.size
        initViews()
    }

    private fun initViews() {
        gameManager = GameManager(main_IMG_hearts.size, main_IMG_cowboys.size, numRows)

        main_FAB_right.setOnClickListener({v -> moveRight()})
        main_FAB_left.setOnClickListener({v -> moveLeft()})
        initTumbleweeds()
        updateUI()
    }

//    private fun startTimer() {
//        handler.postDelayed(runnable, SettingsManager.Difficulty.delay)
//    }

    private fun moveLeft() {
        gameManager.moveLeft()
        updateCowboy()
    }

    private fun moveRight() {
        gameManager.moveRight()
        updateCowboy()
    }

    private fun initTumbleweeds() {
        updateTumbleweeds()
        startTimer()
    }

    private fun updateUI() {
        updateHearts()

        updateCowboy()
        updateTumbleweeds()

        val isHit = gameManager.calcHit()

        if (isHit) {
            toast("You need to Dodge better!")
            SingleSoundPlayer(this).playSound(R.raw.gun_shot)
            vibratePhone()
        }

        main_LBL_score.text = buildString {
            append(gameManager.score)
        }

        if (gameManager.isGameOver) {
            toast("Game Over!")
            stopTimer()
            changeActivity("Game Over!\nYour Score was:", gameManager.score)
        }
    }

    private fun updateHearts() {
        for (i in main_IMG_hearts.indices) {
            if (i < gameManager.timesHit) {
                main_IMG_hearts[i].visibility = View.INVISIBLE
            } else {
                main_IMG_hearts[i].visibility = View.VISIBLE
            }
        }
    }

//    private fun stopTimer() {
//        handler.removeCallbacks(runnable)
//    }

    private fun stopTimer() {
        timerOn = false
        timerJob.cancel()
    }

    private fun startTimer() {
        if (!timerOn) {
            timerOn = true
            timerJob = lifecycleScope.launch {
                while (timerOn) {
                    gameManager.advanceTumbleweeds()
                    updateUI()
                    delay(SettingsManager.Difficulty.delay)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
        BackgroundMusicPlayer.getInstance().pauseMusic()
    }

    override fun onResume() {
        super.onResume()
        BackgroundMusicPlayer.getInstance().playMusic()
    }

    private fun vibratePhone() {
        SignalManager.getInstance().vibrate(Constants.GameLogic.VIBRATION_DURATION)
    }

    private fun toast(text: String){
        SignalManager.getInstance().toast(text)
    }

    private fun updateTumbleweeds() {
        val tumbleweeds = gameManager.tumbleweeds
        for (i in tumbleweeds.indices) {
            for (j in tumbleweeds[i].indices) {
                if (tumbleweeds[i][j].isVisible) {
                    main_IMG_tumbleweeds[i][j].visibility = View.VISIBLE
                    main_IMG_tumbleweeds[i][j].setImageResource(tumbleweeds[i][j].resID)
                }
                else {
                    main_IMG_tumbleweeds[i][j].visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun updateCowboy() {
        for (i in main_IMG_cowboys.indices) {
            if (i == gameManager.cowboyIndex) {
                main_IMG_cowboys[i].visibility = View.VISIBLE
            } else {
                main_IMG_cowboys[i].visibility = View.INVISIBLE
            }
        }
    }

    private fun changeActivity(message: String, score: Int) {
        val intent = Intent(this@MainActivity, GameOverActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(Constants.BundleKeys.SCORE_KEY, score)
        bundle.putString(Constants.BundleKeys.STATUS_KEY, message)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
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
            findViewById(R.id.main_IMG_cowboy3),
            findViewById(R.id.main_IMG_cowboy4),
            findViewById(R.id.main_IMG_cowboy5)
        )

        main_IMG_tumbleweeds = arrayOf(
            arrayOf(
                findViewById(R.id.main_IMG_tumbleweed_0_0),
                findViewById(R.id.main_IMG_tumbleweed_0_1),
                findViewById(R.id.main_IMG_tumbleweed_0_2),
                findViewById(R.id.main_IMG_tumbleweed_0_3),
                findViewById(R.id.main_IMG_tumbleweed_0_4)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_tumbleweed_1_0),
                findViewById(R.id.main_IMG_tumbleweed_1_1),
                findViewById(R.id.main_IMG_tumbleweed_1_2),
                findViewById(R.id.main_IMG_tumbleweed_1_3),
                findViewById(R.id.main_IMG_tumbleweed_1_4)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_tumbleweed_2_0),
                findViewById(R.id.main_IMG_tumbleweed_2_1),
                findViewById(R.id.main_IMG_tumbleweed_2_2),
                findViewById(R.id.main_IMG_tumbleweed_2_3),
                findViewById(R.id.main_IMG_tumbleweed_2_4)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_tumbleweed_3_0),
                findViewById(R.id.main_IMG_tumbleweed_3_1),
                findViewById(R.id.main_IMG_tumbleweed_3_2),
                findViewById(R.id.main_IMG_tumbleweed_3_3),
                findViewById(R.id.main_IMG_tumbleweed_3_4)
            ),
            arrayOf(
                findViewById(R.id.main_IMG_tumbleweed_4_0),
                findViewById(R.id.main_IMG_tumbleweed_4_1),
                findViewById(R.id.main_IMG_tumbleweed_4_2),
                findViewById(R.id.main_IMG_tumbleweed_4_3),
                findViewById(R.id.main_IMG_tumbleweed_4_4)
            )
        )

        main_FAB_right = findViewById(R.id.main_FAB_right)
        main_FAB_left = findViewById(R.id.main_FAB_left)
        main_LBL_score = findViewById(R.id.main_LBL_score)
    }
}
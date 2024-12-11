package com.example.a25a_hw1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a25a_hw1.logic.SettingsManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider

class SettingsActivity : AppCompatActivity() {
    private lateinit var settings_SPN_difficulty: Spinner
    private lateinit var settings_SLD_speed: Slider
    private lateinit var settings_BTN_save: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        findViews()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        settings_SPN_difficulty.setSelection(SettingsManager.Difficulty.tumbleweedsPerRow-1)
        settings_SLD_speed.value = SettingsManager.Difficulty.delay.toFloat()
    }

    private fun findViews() {
        settings_SPN_difficulty = findViewById(R.id.settings_SPN_difficulty)
        settings_SLD_speed = findViewById(R.id.settings_SLD_speed)
        settings_BTN_save = findViewById(R.id.settings_BTN_save)
    }

    private fun initViews() {
        settings_SLD_speed.addOnChangeListener({ _, value, _ -> updateSpeed(value)})
        settings_SPN_difficulty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateDifficulty(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        settings_BTN_save.setOnClickListener({v -> finish()})
    }

    private fun updateDifficulty(pos: Int) {
        SettingsManager.Difficulty.setTumbleweedsPerRow(pos+1)
    }

    private fun updateSpeed(value: Float) {
        SettingsManager.Difficulty.setSpeed(value.toLong())
    }
}

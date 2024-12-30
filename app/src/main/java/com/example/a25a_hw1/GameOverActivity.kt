package com.example.a25a_hw1

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.a25a_hw1.fragments.HighScoreFragment
import com.example.a25a_hw1.interfaces.Callback_HighScoreItemClicked
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapColorScheme
import com.google.android.gms.maps.model.MarkerOptions

class GameOverActivity : AppCompatActivity() {

/*    private lateinit var GameOver_LBL_score: MaterialTextView
    private lateinit var GameOver_BTN_restart: MaterialButton
    private lateinit var GameOver_LBL_title_screen: MaterialTextView*/
    private lateinit var score_FRAME_list: FrameLayout
    private lateinit var score_FRAME_map: FrameLayout

    private lateinit var highScoreFragment: HighScoreFragment
    private lateinit var mapFragment: SupportMapFragment
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        findViews()
        initViews()
    }


    private fun initViews() {
/*        val bundle: Bundle? = intent.extras

        val score = bundle?.getInt("SCORE_KEY", 0)
        val status = bundle?.getString("STATUS_KEY", "Game Over!\nYour Score was:")

        GameOver_LBL_score.text = buildString {
            append(status)
            append("\n")
            append(score)
        }

        GameOver_BTN_restart.setOnClickListener {
            reset()
        }

        GameOver_LBL_title_screen.setOnClickListener {
            titleScreen()
        }*/

        mapFragment = SupportMapFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.score_FRAME_map, mapFragment)
            .commit()

        highScoreFragment = HighScoreFragment()
        highScoreFragment.highScoreItemClicked = object : Callback_HighScoreItemClicked {
            override fun highScoreItemClicked(lat: Double, lon: Double) {

                googleMap?.addMarker(
                    MarkerOptions()
                        .position(LatLng(lat, lon))
                        .title("Afeka")
                ) ?: return

                googleMap?.animateCamera(
                    newLatLngZoom(LatLng(lat, lon), 15f)
                ) ?: return
            }
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.score_FRAME_list, highScoreFragment)
            .commit()

        mapFragment.getMapAsync { p0 ->
            p0.mapColorScheme = MapColorScheme.FOLLOW_SYSTEM
            p0.mapType = GoogleMap.MAP_TYPE_NORMAL
            p0.uiSettings.isMapToolbarEnabled = true
            p0.uiSettings.isZoomControlsEnabled = true
            googleMap = p0
        }

    }

    private fun titleScreen() {
        val intent = Intent(this@GameOverActivity, TitleScreenActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun reset() {
        val intent = Intent(this@GameOverActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun findViews() {
/*        GameOver_LBL_score = findViewById(R.id.GameOver_LBL_score)
        GameOver_BTN_restart = findViewById(R.id.GameOver_BTN_restart)
        GameOver_LBL_title_screen = findViewById(R.id.GameOver_LBL_title_screen)*/
        score_FRAME_list = findViewById(R.id.score_FRAME_list)
        score_FRAME_map = findViewById(R.id.score_FRAME_map)
    }
}
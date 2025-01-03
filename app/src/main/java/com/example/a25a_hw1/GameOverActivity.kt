package com.example.a25a_hw1

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a25a_hw1.adapters.ScoreboardAdapter
import com.example.a25a_hw1.fragments.HighScoreFragment
import com.example.a25a_hw1.interfaces.Callback_HighScoreItemClicked
import com.example.a25a_hw1.logic.ScoreManger
import com.example.a25a_hw1.utilities.Constants
import com.example.a25a_hw1.utilities.SignalManager
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapColorScheme
import com.google.android.gms.maps.model.MarkerOptions
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import com.vmadalin.easypermissions.models.PermissionRequest

class GameOverActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

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

    override fun onResume() {
        super.onResume()

//        val bundle: Bundle? = intent.extras
//        addNewScore(bundle, 32.0, 34.0)
    }

    private fun checkLocationAndAddScore(){
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)){
            addLocationScore()
        }
        else{
            val request = PermissionRequest.Builder(this)
                .code(Constants.Permission.LOCATION_REQUEST_CODE)
                .perms(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
                .build()
            EasyPermissions.requestPermissions(this, request)
        }
    }

    private fun addLocationScore() {
        try {
            googleMap?.isMyLocationEnabled = true
        } catch (e: SecurityException){
            e.printStackTrace()
        }

        highScoreFragment.updateHighScore()
    }

    private fun addNewScore(bundle: Bundle?, lat: Double, lng: Double) {
        val score = bundle?.getInt("SCORE_KEY", 0)
        score?.let { ScoreManger.getInstance().scores.put(it.toString(), LatLng(lat, lng))}
    }



    override fun onPause() {
        super.onPause()
        ScoreManger.getInstance().save()
    }



    private fun initViews() {

        mapFragment = SupportMapFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.score_FRAME_map, mapFragment)
            .commit()

        highScoreFragment = HighScoreFragment()
        highScoreFragment.highScoreItemClicked = object : Callback_HighScoreItemClicked {
            override fun highScoreItemClicked(lat: Double, lon: Double, score: String) {

                googleMap?.addMarker(
                    MarkerOptions()
                        .position(LatLng(lat, lon))
                        .title(score)
                ) ?: return

                googleMap?.animateCamera(
                    newLatLngZoom(LatLng(lat, lon), 8f)
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
            checkLocationAndAddScore()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        SignalManager.getInstance().toast("Location Permissions missing!, Score is not saved!")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {

        if(requestCode == Constants.Permission.LOCATION_REQUEST_CODE){

        }

    }

}
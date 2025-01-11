package com.example.a25a_hw1

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.a25a_hw1.fragments.HighScoreFragment
import com.example.a25a_hw1.interfaces.Callback_HighScoreItemClicked
import com.example.a25a_hw1.interfaces.LocationUpdatedCallback
import com.example.a25a_hw1.logic.ScoreManger
import com.example.a25a_hw1.model.Score
import com.example.a25a_hw1.utilities.Constants
import com.example.a25a_hw1.utilities.LocationDetector
import com.example.a25a_hw1.utilities.SignalManager
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapColorScheme
import com.google.android.gms.maps.model.MarkerOptions
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.models.PermissionRequest

class GameOverActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var score_FRAME_list: FrameLayout
    private lateinit var score_FRAME_map: FrameLayout

    private lateinit var highScoreFragment: HighScoreFragment
    private lateinit var mapFragment: SupportMapFragment
    private var googleMap: GoogleMap? = null
    private var hasLocationPerms: Boolean = false
    private lateinit var locationDetector: LocationDetector
    private var score: Int = 0
    private var isScoreCollected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        findViews()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        handleLocationPerm()
        if (hasLocationPerms) {
            locationDetector.startLocationUpdates()

            if(!isScoreCollected){
                val bundle = intent.extras ?: Bundle()
                score = bundle.getInt("SCORE_KEY", 0)
                isScoreCollected = true

                //check if there is a score on the way
                if (score == 0 && ScoreManger.getInstance().scores.isEmpty()) {
                    highScoreFragment.setEmptyMsg(View.VISIBLE)
                } else {
                    highScoreFragment.setEmptyMsg(View.GONE)
                }
            }
        }
    }

    private fun handleLocationPerm() {
        if (EasyPermissions.hasPermissions(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            onLocationGranted()
        } else {
            val request = PermissionRequest.Builder(this)
                .code(Constants.Permission.LOCATION_REQUEST_CODE)
                .perms(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
                .build()
            EasyPermissions.requestPermissions(this, request)
        }
    }

    override fun onPause() {
        super.onPause()
        ScoreManger.getInstance().save()
        locationDetector.stopLocationUpdates()
    }

    private fun onLocationGranted() {
        hasLocationPerms = true
        enableGoogleMapsLocation()
    }

    private fun addScore(score: Int, lat: Double, lon: Double) {
        if (this.score == 0) return

        ScoreManger.getInstance().scores.add(Score(score, lat, lon))
        ScoreManger.getInstance().sortScores()
        highScoreFragment.updateHighScore()
        this.score = 0
    }

    private fun enableGoogleMapsLocation() {
        try {
            googleMap?.isMyLocationEnabled = true
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun initViews() {

        mapFragment = SupportMapFragment.newInstance()
        locationDetector = LocationDetector(this, object : LocationUpdatedCallback {
            override fun onLocationUpdated(latitude: Double, longitude: Double) {
                addScore(score, latitude, longitude)
            }

        })

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
                    newLatLngZoom(LatLng(lat, lon), 14f)
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
            handleLocationPerm()
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
        score_FRAME_list = findViewById(R.id.score_FRAME_list)
        score_FRAME_map = findViewById(R.id.score_FRAME_map)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        SignalManager.getInstance().toast("Location Permissions missing!, Score is not saved!")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {

        if (requestCode == Constants.Permission.LOCATION_REQUEST_CODE) {
            onLocationGranted()
        }

    }

}
package com.example.a25a_hw1

import android.app.Application
import com.example.a25a_hw1.logic.ScoreManger
import com.example.a25a_hw1.utilities.BackgroundMusicPlayer
import com.example.a25a_hw1.utilities.SignalManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SignalManager.init(this)
        BackgroundMusicPlayer.init(this)
        BackgroundMusicPlayer.getInstance().setResourceId(R.raw.background_music)
        ScoreManger.init(this)
        //Prefy.getInstance().remove(Constants.SP_keys.SCOREBOARD_KEY)
        ScoreManger.getInstance().load()

    }

    override fun onTerminate() {
        super.onTerminate()
        BackgroundMusicPlayer.getInstance().stopMusic()
    }
}
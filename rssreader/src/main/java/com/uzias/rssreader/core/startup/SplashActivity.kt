package com.uzias.rssreader.core.startup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.uzias.rssreader.R
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar.setProgress(0)
        tvProgPerc.setText("")

        timer = Timer()
        timer.schedule(updateProgress(), 0,  50)

    }

    lateinit var timer: Timer
    var i =1
    inner class updateProgress :  TimerTask() {
        override fun run () {

            if(i< 100) {
                runOnUiThread {
                    tvProgPerc.text = "$i%"
                }
                progressBar.setProgress(i)
                i++
            }
            else {
                timer.cancel()
                val mainIntent = Intent(this@SplashActivity, LoginActivity::class.java)
                this@SplashActivity.startActivity(mainIntent)
                this@SplashActivity.finish()
            }
        }
    }






}

package com.example.rssfeeder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var rotate: Animation = AnimationUtils.loadAnimation(
            this@SplashActivity,
            R.anim.loading_circle)


        loading.startAnimation(rotate)
        rotateGear(rotate)
    }

    fun rotateGear(rotate: Animation) {
        rotate.setAnimationListener(
            object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {

                }
                override fun onAnimationEnd(p0: Animation?) {
                    var intentDetails = Intent()
                    intentDetails.setClass(
                        this@SplashActivity,
                        LoginActivity::class.java)

                    startActivity(intentDetails)
                    finish()
                }
                override fun onAnimationStart(p0: Animation?) {

                }
            }
        )
    }*/

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar.setProgress(0)
        tvProgPerc.setText("")

        timer = Timer()
        timer.schedule(updateProgress(), 0,  50)
    }




}

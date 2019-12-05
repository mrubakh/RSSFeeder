package com.example.rssfeeder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_animation_screen.*

class AnimationScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_screen)

        var rotate: Animation = AnimationUtils.loadAnimation(
            this@AnimationScreen,
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
                        this@AnimationScreen,
                        LoginActivity::class.java)

                    startActivity(intentDetails)
                    finish()
                }
                override fun onAnimationStart(p0: Animation?) {

                }
            }
        )
    }

}

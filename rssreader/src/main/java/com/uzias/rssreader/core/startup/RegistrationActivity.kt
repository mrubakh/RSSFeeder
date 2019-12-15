package com.uzias.rssreader.core.startup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.uzias.rssreader.R
import com.uzias.rssreader.feed.presentation.view.FeedActivity
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    fun registerClick(v: View) {

        if (!isRegistrationValid()) {
            return
        }
        startActivity(Intent(this@RegistrationActivity, FeedActivity::class.java))

    }

    fun goToLogin(v: View){
        startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
    }

    private fun isRegistrationValid(): Boolean {
        return when {
            regUser.text.isEmpty() -> {
                regUser.error = getString(R.string.user_empty)
                false
            }
            regPass.text.isEmpty() -> {
                regPass.error = getString(R.string.pass_empty)
                false
            }

            else -> true
        }
    }
}

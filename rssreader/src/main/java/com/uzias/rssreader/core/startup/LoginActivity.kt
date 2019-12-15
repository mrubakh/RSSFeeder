package com.uzias.rssreader.core.startup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.UserProfileChangeRequest
import com.uzias.rssreader.R
import com.uzias.rssreader.feed.presentation.view.FeedActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginClick(v: View) {

        if(!isLoginValid()){
            return
        }
            //Open main Activity
            startActivity(Intent(this@LoginActivity, FeedActivity::class.java))
    }

    fun goToRegistration(v: View){
        startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
    }



    fun userNameFromEmail(email: String) = email.substringBefore("@")

    private fun isLoginValid(): Boolean {
        return when {
            username.text.isEmpty() -> {
               username.error = "This field can not be empty"
                false
            }
            password.text.isEmpty() -> {
                password.error = "This field can not be empty"
                false
            }
            else -> true
        }
    }


}
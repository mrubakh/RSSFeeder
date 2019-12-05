package com.example.rssfeeder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginClick(v: View) {

        if(!isFormValid()){
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            username.text.toString(),
            password.text.toString()
        ).addOnSuccessListener {
            Toast.makeText(
                this@LoginActivity, "Login Successful",
                Toast.LENGTH_LONG
            ).show()

            //Open main Activity
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))

        }.addOnFailureListener{
            Toast.makeText(
                this@LoginActivity, "Error: ${it.message}",
                Toast.LENGTH_LONG
            ).show()
        }


    }

    fun registerClick(v: View) {

        if (!isFormValid()) {
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            username.text.toString(), password.text.toString()
        ).addOnSuccessListener {
            val user = it.user

            user?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(userNameFromEmail(username.text.toString()))
                    .build()
            )

            Toast.makeText(
                this@LoginActivity, "Registration Successful.",
                Toast.LENGTH_LONG
            ).show()
        }.addOnFailureListener {
            Toast.makeText(
                this@LoginActivity, "Error: ${it.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun userNameFromEmail(email: String) = email.substringBefore("@")

    private fun isFormValid(): Boolean {
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
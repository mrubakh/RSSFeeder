package com.uzias.rssreader.core.startup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.UserProfileChangeRequest
import com.uzias.rssreader.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

//    fun registerClick(v: View) {
//
//        if (!isRegistrationValid()) {
//            return
//        }
//
//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
//            regUser.text.toString(), regPass.text.toString()
//        ).addOnSuccessListener {
//            val user = it.user
//
//            user?.updateProfile(
//                UserProfileChangeRequest.Builder()
//                    .setDisplayName(userNameFromEmail(username.text.toString()))
//                    .build()
//            )
//
//            Toast.makeText(
//                this@RegistrationActivity, "Registration Successful.",
//                Toast.LENGTH_LONG
//            ).show()
//        }.addOnFailureListener {
//            Toast.makeText(
//                this@RegistrationActivity, "Error: ${it.message}",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }

    fun goToLogin(v: View){
        startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
    }

    fun userNameFromEmail(email: String) = email.substringBefore("@")

    private fun isRegistrationValid(): Boolean {
        return when {
            regUser.text.isEmpty() -> {
                regUser.error = "This field can not be empty"
                false
            }
            regPass.text.isEmpty() -> {
                regPass.error = "This field can not be empty"
                false
            }
//            !regPass.text.equals(regPass2.text) -> {
//                Log.i("PASSWORDS","${regPass.text} ${regPass2.text}")
//                regPass2.error = "The entered passwords do not match"
//                false
//            }
            else -> true
        }
    }
}

package com.example.rssfeeder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rssfeeder.data.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_blog.*

class AddBlogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blog)

        btnAdd.setOnClickListener {
            addBlog()
        }
    }


    private fun addBlog() {
        //Finish() added to test add button
        finish()
        /*var blog  = Account(
            FirebaseAuth.getInstance().currentUser!!.uid,
            FirebaseAuth.getInstance().currentUser!!.displayName!!,
            etTitle.text.toString(),
            etBody.text.toString(),
            imageUrl
        )

        var postsCollection: CollectionReference =
            FirebaseFirestore.getInstance().collection("posts")

        postsCollection.add(blog).addOnSuccessListener {
            Toast.makeText(this@AddBlogActivity,
                "Upload Successful", Toast.LENGTH_LONG).show()

            finish()
        }.addOnFailureListener {
            Toast.makeText(this@AddBlogActivity,
                "Upload Failed: ${it.message}", Toast.LENGTH_LONG).show()
        }
     */
    }
}

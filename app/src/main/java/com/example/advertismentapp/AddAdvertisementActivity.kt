package com.example.advertismentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import com.example.advertismentapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_advertisement.*

class AddAdvertisementActivity : AppCompatActivity() {
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_advertisement)
        user = FirebaseAuth.getInstance().currentUser!!

        val callback = onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@AddAdvertisementActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    public fun addAdvertisement(view: View) {

        val newAdvertisement = Advertisement(add_title.text.toString(), add_description.text.toString(), add_imgurl.text.toString(), "${'$'}${add_price.text.toString()}", user.uid, "" )

        FirebaseFirestore.getInstance().collection("advertisements").add(newAdvertisement).addOnSuccessListener { run {
            val intent = Intent(this@AddAdvertisementActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        } }


    }

}
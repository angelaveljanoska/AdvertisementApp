package com.example.advertismentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_advertisement.*
import kotlinx.android.synthetic.main.activity_advertisement_details.*

class AdvertisementDetailsActivity : AppCompatActivity() {
    private lateinit var advertisement: Advertisement
    var currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertisement_details)
        var title = intent.getStringExtra("title")
        var description = intent.getStringExtra("description")
        var imageUrl = intent.getStringExtra("imageUrl")
        var price = intent.getStringExtra("price")
        var creatorId = intent.getStringExtra("creatorId")
        var documentId = intent.getStringExtra("documentId")

        advertisement = Advertisement(
                title, description, imageUrl, price, creatorId, documentId!!
        )

        rmv_btn.isVisible = currentUser?.uid == advertisement.creatorId
        add_offer.isVisible = !rmv_btn.isVisible

        updateInfo()
        loadOffers()

    }

    private fun updateInfo() {
        println(advertisement.toString())
        advertisement_title.text = advertisement.title
        advertisement_description.text = advertisement.description
        advertisement_price.text = advertisement.price
        val imageUrl = if (!advertisement.imageUrl.equals("")) advertisement.imageUrl else "https://image.shutterstock.com/image-vector/ui-image-placeholder-wireframes-apps-260nw-1037719204.jpg"
        Picasso.get().load(imageUrl).into(advertisement_image)
    }

    public fun submitOffer(view: View) {
        val newOffer = Offer(
            advertisement.creatorId,
            FirebaseAuth.getInstance().currentUser!!.uid,
            "${'$'}${offer_amount.text.toString()}",
            advertisement.documentId
        )
        FirebaseFirestore.getInstance().collection("offers").add(newOffer).addOnSuccessListener {
            run {
                Toast.makeText(
                    this@AdvertisementDetailsActivity,
                    "Successfully placed an offer!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this@AdvertisementDetailsActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    public fun removeAdvertisement(view: View) {
        FirebaseFirestore.getInstance().collection("advertisements").document(advertisement.documentId).delete().addOnSuccessListener {
            run {
                Toast.makeText(
                    this@AdvertisementDetailsActivity,
                    "This advertisement has been removed!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this@AdvertisementDetailsActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    public fun loadOffers() {
        if(currentUser!!.uid == advertisement.creatorId) {
            offer_layout.isVisible = false;

            FirebaseFirestore.getInstance().collection("offers").whereEqualTo("advCreatorId", currentUser?.uid)
                .whereEqualTo("advertisementId", advertisement.documentId)
                .get()
                .addOnSuccessListener { data ->
                    run {
                        val offers = data.toObjects(Offer::class.java)
                        for (offer in offers) {
                            val textView = TextView(this)
                            textView.text = "Buyer with ID: ${offer.buyerId} has offered ${offer.amount}"
                            textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            textView.textSize = 15F
                            textView.setPadding(15, 25, 15, 25);
                            textView.setBackgroundColor(resources.getColor(R.color.colorAccent))
                            textView.setTextColor(resources.getColor(R.color.white))
                            loaded_offers.addView(textView)
                        }

                    }
                }
        } else {
            FirebaseFirestore.getInstance().collection("offers")
                .whereEqualTo("advertisementId", advertisement.documentId).whereEqualTo("buyerId", currentUser?.uid)
                .get()
                .addOnSuccessListener { data ->
                    run {
                        val offers = data.toObjects(Offer::class.java)
                        for (offer in offers) {
                            val textView = TextView(this)
                            textView.text = "Your latest offer is ${offer.amount}"
                            textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            textView.textSize = 15F
                            textView.setPadding(15, 25, 15, 25);
                            textView.setBackgroundColor(resources.getColor(R.color.colorAccent))
                            textView.setTextColor(resources.getColor(R.color.white))
                            loaded_offers.addView(textView)
                        }
                    }
                }
        }

    }
}
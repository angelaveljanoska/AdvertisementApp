package com.example.advertismentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.advertismentapp.R.layout.activity_main
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var advertisementsAdapter: RecyclerView.Adapter<AdvertisementsAdapter.ViewHolder>? = null

    private var searchInput: String = ""
    private lateinit var fetchedElements: List<Advertisement>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        layoutManager = LinearLayoutManager(this)
        advertisement_list_recycler.layoutManager = layoutManager
        advertisementsAdapter = AdvertisementsAdapter()
        advertisement_list_recycler.adapter = advertisementsAdapter

        FirebaseFirestore.getInstance().collection("advertisements")
            .get()
            .addOnSuccessListener { data ->
                fetchedElements = data.toObjects(Advertisement::class.java)
                updateAdvertisements()
            }
        search.setOnQueryTextListener(object : OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                searchInput = p0?: ""
                updateAdvertisements()
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                updateAdvertisements()
                return true
            }
        }

        )


    }

    private fun updateAdvertisements() {
        (advertisement_list_recycler.adapter as AdvertisementsAdapter).updateList(fetchedElements, searchInput)
        (advertisement_list_recycler.adapter as RecyclerView.Adapter).notifyDataSetChanged()
    }

    fun addNewAdv(view: View) {
        val intent = Intent(view.context, AddAdvertisementActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun logOut(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }
}
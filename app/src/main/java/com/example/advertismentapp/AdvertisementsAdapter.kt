package com.example.advertismentapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_layout.view.*

class AdvertisementsAdapter() : RecyclerView.Adapter<AdvertisementsAdapter.ViewHolder>() {

    private var advertisements: List<Advertisement> = mutableListOf()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertisementsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdvertisementsAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = advertisements[position].title
        holder.itemDetail.text = advertisements[position].description
        val imageUrl = if (!advertisements[position].imageUrl.equals("")) advertisements[position].imageUrl else "https://image.shutterstock.com/image-vector/ui-image-placeholder-wireframes-apps-260nw-1037719204.jpg"
        Picasso.get().load(imageUrl).into(holder.itemImage)
        holder.itemPrice.text = advertisements[position].price
    }

    override fun getItemCount(): Int {
        return advertisements.size
    }

    fun updateList(list: List<Advertisement>, search: String) {
        this.advertisements = list.filter { it.title!!.contains(search) }
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.item_title
        var itemDetail: TextView = itemView.item_detail
        var itemImage: ImageView = itemView.item_image
        var itemPrice: TextView = itemView.item_price

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val intent = Intent(itemView.context, AdvertisementDetailsActivity::class.java)
                intent.putExtra("title", advertisements[position].title)
                intent.putExtra("description", advertisements[position].description)
                intent.putExtra("imageUrl", advertisements[position].imageUrl)
                intent.putExtra("price", advertisements[position].price)
                intent.putExtra("creatorId", advertisements[position].creatorId)
                intent.putExtra("documentId", advertisements[position].documentId)
                itemView.context.startActivity(intent)

            }
        }

    }

}
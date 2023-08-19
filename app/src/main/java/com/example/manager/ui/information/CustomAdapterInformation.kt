package com.example.manager.ui.information

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manager.R
import com.example.manager.data.information.Information

class CustomAdapterInformation(private val itemsList: List<Information>) : RecyclerView.Adapter<CustomAdapterInformation.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.information_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList[position]

        // sets the image to the imageview from our itemHolder class
        for(i in 0 until item.settings.size){
            when(item.settings[i]){
                "groceries" -> holder.img.setImageResource(R.drawable.shopping_cart)
            }
        }

        holder.value.text = item.value.toString() + "€"

        holder.title.text = item.title
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val img: ImageView = itemView.findViewById(R.id.InfoCardImg)
        val title: TextView = itemView.findViewById(R.id.InfoCardTitle)
        val value: TextView = itemView.findViewById(R.id.InfoCardMoney)

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }
}
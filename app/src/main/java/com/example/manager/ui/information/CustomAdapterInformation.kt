package com.example.manager.ui.information

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manager.R
import com.example.manager.data.information.Information

class CustomAdapterInformation(private val itemsList: List<Information>, private val context: Context) : RecyclerView.Adapter<CustomAdapterInformation.ViewHolder>() {

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

        if(item.id == -1){
            //if it's the first item, we show the date
            holder.date.text = dateFormat(item)
        }

        if(holder.date.text.isEmpty()) {
            // sets the image to the imageview from our itemHolder class
            for (i in 0 until item.settings.size) {
                when (item.settings[i]) {
                    "groceries" -> holder.img.setImageResource(R.drawable.shopping_cart)
                }
            }
            holder.value.text = item.value.toString() + "€"

            holder.title.text = item.title
        }else{
            holder.img.visibility = View.GONE
            holder.value.visibility = View.GONE
            holder.title.visibility = View.GONE
        }
    }

    //formats the date
    private fun dateFormat(item: Information): String{
        val fullDate = item.date.split("-")
        val day = fullDate[2].toInt()
        val month = when(fullDate[1].toInt()){
            1 -> context.getString(R.string.jan)
            2 -> context.getString(R.string.feb)
            3 -> context.getString(R.string.mar)
            4 -> context.getString(R.string.apr)
            5 -> context.getString(R.string.may)
            6 -> context.getString(R.string.jun)
            7 -> context.getString(R.string.jul)
            8 -> context.getString(R.string.aug)
            9 -> context.getString(R.string.sep)
            10 -> context.getString(R.string.oct)
            11 -> context.getString(R.string.nov)
            12 -> context.getString(R.string.dec)
            else -> context.getString(R.string.error)
        }
        return "$day $month"
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val img: ImageView = itemView.findViewById(R.id.InfoCardImg)
        val title: TextView = itemView.findViewById(R.id.InfoCardTitle)
        val value: TextView = itemView.findViewById(R.id.InfoCardMoney)
        val date: TextView = itemView.findViewById(R.id.InfoCardDate)

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }
}
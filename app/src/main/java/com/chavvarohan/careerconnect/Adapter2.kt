package com.chavvarohan.careerconnect

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chavvarohan.careerconnect.R
import javax.sql.DataSource

class Adapter2(private var info: List<Info>) : RecyclerView.Adapter<Adapter2.InfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_items_2, parent, false)
        return InfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val currentItem = info[position]
        holder.textViewTitle.text = currentItem.title
        holder.textViewDate.text = currentItem.date
        holder.textViewDescription.text = currentItem.description

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(currentItem.image)
            .placeholder(R.drawable.placeholder)  // Placeholder while image loads
            .error(R.drawable.placeholder)  // Fallback if the image fails to load
            .into(holder.imageTitle)

    }

    override fun getItemCount(): Int {
        return info.size
    }

    fun updateData(newInfoList: List<Info>) {
        info = newInfoList
        notifyDataSetChanged()
    }

    inner class InfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageTitle: ImageView = itemView.findViewById(R.id.image_view_pic)
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val textViewDate: TextView = itemView.findViewById(R.id.text_view_date)
        val textViewDescription: TextView = itemView.findViewById(R.id.text_view_description)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = info[position]

                    val intent = Intent(itemView.context, InfoViewActivity::class.java).apply {
                        putExtra("title", currentItem.title)
                        putExtra("date", currentItem.date)
                        putExtra("description", currentItem.description)
                        putExtra("image", currentItem.image)
                        putExtra("link", currentItem.link)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}

package com.chavvarohan.careerconnect

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter2(private val info: List<Info>) : RecyclerView.Adapter<Adapter2.InfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_items_2, parent, false)
        return InfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val currentItem = info[position]
        holder.textViewTitle.text = currentItem.title
        holder.textViewOfferedBy.text = currentItem.offeredBy
        holder.textViewDescription.text = currentItem.description

        // Load image here, assuming a placeholder for now
        holder.imageTitle.setImageResource(R.color.black)
    }

    override fun getItemCount(): Int {
        return info.size
    }

    inner class InfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageTitle: ImageView = itemView.findViewById(R.id.image_view_pic)
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val textViewOfferedBy: TextView = itemView.findViewById(R.id.text_view_)
        val textViewDescription: TextView = itemView.findViewById(R.id.text_view_description)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = info[position]

                    val intent = Intent(itemView.context, InfoViewActivity::class.java).apply {
                        putExtra("title", currentItem.title)
                        putExtra("offeredBy", currentItem.offeredBy)
                        putExtra("description", currentItem.description)
                        putExtra("image", currentItem.image)
                        putExtra("link", currentItem.link)  // Pass the link field
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}

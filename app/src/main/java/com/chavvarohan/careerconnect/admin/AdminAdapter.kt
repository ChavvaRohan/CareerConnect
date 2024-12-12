package com.chavvarohan.careerconnect.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chavvarohan.careerconnect.user.Info
import com.chavvarohan.careerconnect.R

class AdminAdapter(private val list: ArrayList<Info>) : RecyclerView.Adapter<AdminAdapter.HackathonViewHolder>() {

    var onDeleteClick: ((Info) -> Unit)? = null
    var onItemClick: ((Info) -> Unit)? = null

    fun updateData(newList: List<Info>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HackathonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_edit, parent, false)
        return HackathonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HackathonViewHolder, position: Int) {
        val currentItem = list[position]
        holder.description.text = currentItem.description
        holder.date.text = currentItem.date
        holder.title.text = currentItem.title

        Glide.with(holder.itemView.context)
            .load(currentItem.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.imageView)

        holder.deleteButton.setOnClickListener {
            onDeleteClick?.invoke(currentItem)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HackathonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.text_view_title_admin)
        val description: TextView = itemView.findViewById(R.id.text_view_description_admin)
        val imageView: ImageView = itemView.findViewById(R.id.image_view_circulars_picture_admin)
        val deleteButton: Button = itemView.findViewById(R.id.button_delete)
        val date: TextView = itemView.findViewById(R.id.text_view_date_admin)
    }
}


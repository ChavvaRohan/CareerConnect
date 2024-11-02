package com.chavvarohan.careerconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chavvarohan.careerconnect.user.Info

class AdminPlacementAdapter(private val list: ArrayList<PlacementInfo>) : RecyclerView.Adapter<AdminPlacementAdapter.HackathonViewHolder>() {

    var onDeleteClick: ((PlacementInfo) -> Unit)? = null
    var onItemClick: ((PlacementInfo) -> Unit)? = null

    fun updateData(newList: List<PlacementInfo>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HackathonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_edit_placement, parent, false)
        return HackathonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HackathonViewHolder, position: Int) {
        val currentItem = list[position]
        holder.description.text = currentItem.description
        holder.date.text = currentItem.date
        holder.title.text = currentItem.company

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
        val deleteButton: Button = itemView.findViewById(R.id.button_delete)
        val date: TextView = itemView.findViewById(R.id.text_view_date_admin)
    }
}
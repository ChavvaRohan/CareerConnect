package com.chavvarohan.careerconnect.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chavvarohan.careerconnect.databinding.ListItemsBinding

class Adapter(private val data: List<Data>) : RecyclerView.Adapter<Adapter.DataViewHolder>() {

    private var mListener: OnItemClickListener? = null

    // Interface for click events
    interface OnItemClickListener {
        fun onRegisterClick(position: Int)
    }

    // Method to set the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    // Create view holder instance and inflate the layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    // Bind data to the view holder
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(data[position])
    }

    // Return the total number of items
    override fun getItemCount(): Int {
        return data.size
    }

    // ViewHolder class to hold the view references
    inner class DataViewHolder(private val binding: ListItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            // Register click listener on the card view
            binding.cardViewRegister.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    mListener?.onRegisterClick(position)
                }
            }
        }

        // Method to bind data to the views
        fun bind(data: Data) {
            binding.textViewCompanyName.text = data.companyName
            binding.textViewDate.text = data.date
            // Optionally bind other data (e.g., description, link) if needed
        }
    }
}

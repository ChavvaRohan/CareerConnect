package com.chavvarohan.careerconnect

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chavvarohan.careerconnect.databinding.ListItemsBinding

class Adapter(private val data: List<Data>) : RecyclerView.Adapter<Adapter.DataViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {

        fun onRegisterClick(position: Int)

        fun onInfoClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class DataViewHolder(
        private val binding: ListItemsBinding,
        private val listener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageViewInfo.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onInfoClick(position)
                }
            }

            binding.cardViewRegister.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onRegisterClick(position)
                }
            }
        }

        fun bind(data: Data) {
            binding.textViewCompanyName.text = data.companyName
            binding.textViewQuote.text = data.companyQuote
        }
    }
}

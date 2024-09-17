package com.chavvarohan.careerconnect.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.chavvarohan.careerconnect.R
import com.chavvarohan.careerconnect.databinding.ActivityInfoViewBinding

class InfoViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from the intent
        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val description = intent.getStringExtra("description")
        val imageUrl = intent.getStringExtra("image")  // Changed to string for URL
        val link = intent.getStringExtra("link")

        // Set data to views
        binding.title.text = title
        binding.date.text = date
        binding.description.text = description

        // Load image using Glide
        if (imageUrl != null) {
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)  // Placeholder while image loads
                .error(R.drawable.placeholder)  // Fallback if the image fails to load
                .into(binding.image)
        } else {
            binding.image.setImageResource(R.drawable.placeholder)  // Fallback if no imageUrl is provided
        }

        // Set up the button click listener to open the link
        binding.register.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }

    }
}

package com.chavvarohan.careerconnect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chavvarohan.careerconnect.databinding.ActivityInfoViewBinding

class InfoViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from the intent
        val title = intent.getStringExtra("title")
        val offeredBy = intent.getStringExtra("offeredBy")
        val description = intent.getStringExtra("description")
        val image = intent.getStringExtra("image")
        val link = intent.getStringExtra("link")  // Get the link

        // Set data to views
        binding.title.text = title
        binding.offeredBy.text = offeredBy
        binding.description.text = description

        // Load image into ImageView
        binding.image.setImageResource(R.color.black)

        // Set up the button click listener to open the link
        binding.register.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
    }
}

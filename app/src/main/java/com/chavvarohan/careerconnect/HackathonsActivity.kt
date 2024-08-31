package com.chavvarohan.careerconnect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chavvarohan.careerconnect.databinding.ActivityHackathonsBinding

class HackathonsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHackathonsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHackathonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sample data
        val hackathonList = listOf(
            Info("image_url_1", "Hackathon 1", "Organized by X", "Description 1","https://www.google.co.in/?client=safari&channel=mac_bm"),
            Info("image_url_2", "Hackathon 2", "Organized by Y", "Description 2","https://www.google.co.in/?client=safari&channel=mac_bm"),
            Info("image_url_3", "Hackathon 3", "Organized by Z", "Description 3","https://www.google.co.in/?client=safari&channel=mac_bm")
        )

        // Setting up the RecyclerView
        val adapter = Adapter2(hackathonList)
        binding.recyclerViewHackathon.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHackathon.adapter = adapter

    }
}

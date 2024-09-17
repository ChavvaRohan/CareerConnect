package com.chavvarohan.careerconnect.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chavvarohan.careerconnect.databinding.ActivityPlacementsBinding

class PlacementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacementsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = listOf(
            Data("Accenture", "Work at the heart of the change.", "Some Text","https://www.google.com/","25/02/2003"),
            Data("NovaTech Solutions", "Innovating tomorrow, today.", "NovaTech Solutions specializes in cutting-edge software development and IT consulting services, helping businesses stay ahead of the technological curve.","https://www.google.com/","25/02/2003"),
            Data("EcoWave Industries", "Harnessing nature, powering the future.", "EcoWave Industries develops sustainable energy solutions, focusing on renewable sources like wind, solar, and tidal power.","https://www.google.com/","23/02/2003"),
            Data("Medivista Health", "Your health, our mission.", "Medivista Health offers advanced healthcare services and innovative medical devices to improve patient outcomes and quality of life.","https://www.google.com/","23/02/2003"),
            Data("GreenLeaf Organics", "Pure, fresh, and natural.", "GreenLeaf Organics produces high-quality organic food products, promoting healthy living and sustainable farming practices.","https://www.google.com/","11/02/2003"),
            Data("CyberGuard Systems","Securing your digital frontier.","CyberGuard Systems provides top-tier cybersecurity solutions to protect businesses and individuals from digital threats.","https://www.google.com/","11/02/2003"),

        )

        binding.recyclerViewPlacements.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(data)
        binding.recyclerViewPlacements.adapter = adapter

        adapter.setOnItemClickListener(object : Adapter.OnItemClickListener {

            override fun onRegisterClick(position: Int) {
                val intent = Intent(this@PlacementsActivity, ApplyActivity::class.java)
                intent.putExtra("companyName", data[position].companyName)
                intent.putExtra("quote", data[position].companyQuote)
                intent.putExtra("description", data[position].description)
                intent.putExtra("link", data[position].link)
                startActivity(intent)
            }
        })
    }
}


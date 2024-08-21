package com.chavvarohan.careerconnect

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
            Data("Accenture", "Work at the heart of the change.", "Some Text"),
            Data("NovaTech Solutions", "Innovating tomorrow, today.", "NovaTech Solutions specializes in cutting-edge software development and IT consulting services, helping businesses stay ahead of the technological curve."),
            Data("EcoWave Industries", "Harnessing nature, powering the future.", "EcoWave Industries develops sustainable energy solutions, focusing on renewable sources like wind, solar, and tidal power."),
            Data("Medivista Health", "Your health, our mission.", "Medivista Health offers advanced healthcare services and innovative medical devices to improve patient outcomes and quality of life."),
            Data("GreenLeaf Organics", "Pure, fresh, and natural.", "GreenLeaf Organics produces high-quality organic food products, promoting healthy living and sustainable farming practices."),
            Data("CyberGuard Systems","Securing your digital frontier.","CyberGuard Systems provides top-tier cybersecurity solutions to protect businesses and individuals from digital threats.")
        )

        binding.recyclerViewPlacements.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(data)
        binding.recyclerViewPlacements.adapter = adapter

        adapter.setOnItemClickListener(object : Adapter.OnItemClickListener {

            override fun onInfoClick(position: Int) {
                val intent = Intent(this@PlacementsActivity, InfoActivity::class.java)
                intent.putExtra("description", data[position].description)
                startActivity(intent)
            }

            override fun onRegisterClick(position: Int) {
                val intent = Intent(this@PlacementsActivity, ApplyActivity::class.java)
                intent.putExtra("companyName", data[position].companyName)
                intent.putExtra("quote", data[position].companyQuote)
                startActivity(intent)
            }
        })
    }
}


package com.chavvarohan.careerconnect

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chavvarohan.careerconnect.databinding.ActivityHomeBinding
import com.chavvarohan.careerconnect.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardViewPlacements.setOnClickListener {
            startActivity(Intent(this, PlacementsActivity::class.java))
        }

        binding.cardViewProfilePic.setOnClickListener {
            startActivity(Intent(this,FormActivity::class.java))
        }

        binding.cardViewHackathons.setOnClickListener {
            startActivity(Intent(this,HackathonsActivity::class.java))
        }

    }
}
package com.chavvarohan.careerconnect.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chavvarohan.careerconnect.databinding.ActivityHomeBinding

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
            startActivity(Intent(this, FormActivity::class.java))
        }

        binding.cardViewHackathons.setOnClickListener {
            startActivity(Intent(this, HackathonsActivity::class.java))
        }

        binding.imageViewSettings.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))

        }

        binding.cardViewTrainings.setOnClickListener {
            startActivity(Intent(this, TrainingActivity::class.java))
        }

        binding.cardViewHigherEducation.setOnClickListener {
            startActivity(Intent(this, HigherEducationActivity::class.java))
        }

        binding.cardViewInternships.setOnClickListener {
            startActivity(Intent(this, InternshipsActivity::class.java))
        }

    }
}
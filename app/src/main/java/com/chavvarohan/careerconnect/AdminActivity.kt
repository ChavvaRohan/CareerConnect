package com.chavvarohan.careerconnect

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chavvarohan.careerconnect.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardViewEvents.setOnClickListener {
            startActivity(Intent(this, EventsAdminActivity::class.java))
        }

        binding.cardViewPlacements.setOnClickListener {
            startActivity(Intent(this, PlacementsAdminActivity::class.java))
        }

        binding.cardViewHackathons.setOnClickListener {
            startActivity(Intent(this, HackathonsAdminActivity::class.java))
        }

        binding.cardViewTrainings.setOnClickListener {
            startActivity(Intent(this, TrainingAdminActivity::class.java))
        }

        binding.cardViewHigherEducation.setOnClickListener {
            startActivity(Intent(this, HigherEducationAdminActivity::class.java))
        }

        binding.cardViewInternships.setOnClickListener {
            startActivity(Intent(this, InternshipAdminActivity::class.java))
        }


    }
}
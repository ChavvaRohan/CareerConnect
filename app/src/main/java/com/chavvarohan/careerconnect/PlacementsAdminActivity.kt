package com.chavvarohan.careerconnect

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chavvarohan.careerconnect.databinding.ActivityPlacementsAdminBinding

class PlacementsAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacementsAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacementsAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
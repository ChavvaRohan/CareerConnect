package com.chavvarohan.careerconnect.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chavvarohan.careerconnect.databinding.ActivityPlacementsAdminBinding

class PlacementsAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacementsAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacementsAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
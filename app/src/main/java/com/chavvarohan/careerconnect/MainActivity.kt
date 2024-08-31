package com.chavvarohan.careerconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chavvarohan.careerconnect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignIn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.textViewRegister.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

    }
}
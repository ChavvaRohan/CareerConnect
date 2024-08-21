package com.chavvarohan.careerconnect

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chavvarohan.careerconnect.databinding.ActivityApplyBinding

class ApplyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityApplyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle : Bundle? = intent.extras
        val companyName = bundle!!.getString("companyName")
        val quote = bundle!!.getString("quote")

        binding.textViewCompany.text = companyName
        binding.textViewQuote.text = quote

    }
}
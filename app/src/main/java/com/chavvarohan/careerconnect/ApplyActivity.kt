package com.chavvarohan.careerconnect

import android.content.Intent
import android.net.Uri
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

        val bundle: Bundle? = intent.extras
        val companyName = bundle!!.getString("companyName")
        val quote = bundle.getString("quote")
        val description = bundle.getString("description")
        val link = bundle.getString("link")

        binding.textViewCompany.text = companyName
        binding.textViewQuote.text = quote
        binding.textViewDescription.text = description
        binding.buttonApply.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
    }
}
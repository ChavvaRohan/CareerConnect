package com.chavvarohan.careerconnect.user

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chavvarohan.careerconnect.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val ref = db.collection("users").document(userId)
            ref.get().addOnSuccessListener { document ->
                if (document != null) {
                    binding.textViewName.text = document.getString("name")
                    binding.textViewEmail.text = document.getString("email")
                }
            }
        }

        binding.cardViewPlacements.setOnClickListener {
            startActivity(Intent(this, PlacementsActivity::class.java))
        }

        binding.cardViewHackathons.setOnClickListener {
            startActivity(Intent(this, HackathonsActivity::class.java))
        }

        binding.imageViewSettings.setOnClickListener {
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

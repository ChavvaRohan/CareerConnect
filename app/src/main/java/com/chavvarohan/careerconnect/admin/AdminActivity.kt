package com.chavvarohan.careerconnect.admin

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chavvarohan.careerconnect.MainActivity
import com.chavvarohan.careerconnect.databinding.ActivityAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminBinding

    private lateinit var auth : FirebaseAuth

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val ref = db.collection("users").document(userId)
            ref.get().addOnSuccessListener { document ->
                if (document != null) {
                    binding.textViewAdminName.text = document.getString("name")
                    binding.textViewAdminEmail.text = document.getString("email")
                }
            }
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

        binding.cardViewLogout.setOnClickListener {
            AlertDialog.Builder(this@AdminActivity)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    auth.signOut()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }

    }
}
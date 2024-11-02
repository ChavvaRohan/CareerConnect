package com.chavvarohan.careerconnect.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chavvarohan.careerconnect.MainActivity
import com.chavvarohan.careerconnect.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingsBinding

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.imageViewBack.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.constraintLayoutProfile.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }

        binding.constraintLayoutLogout.setOnClickListener {

            AlertDialog.Builder(this@SettingsActivity)
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

        binding.constraintLayoutResume.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://login-with-firebase-data-554a6.web.app"))
            startActivity(intent)
        }

        binding.constraintLayoutSupport.setOnClickListener {

            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","rohanchavva@gmail.com", null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Request")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }

    }
}
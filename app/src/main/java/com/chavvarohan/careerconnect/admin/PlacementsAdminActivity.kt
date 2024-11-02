package com.chavvarohan.careerconnect.admin

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chavvarohan.careerconnect.ViewPlacementsUploadActivity
import com.chavvarohan.careerconnect.databinding.ActivityPlacementsAdminBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PlacementsAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacementsAdminBinding

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacementsAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {
            if (validateInput()) {
                uploadPlacements()
                binding.progressBar.visibility = View.VISIBLE
            }
        }

        binding.buttonViewAnnouncements.setOnClickListener {
            startActivity(Intent(this,ViewPlacementsUploadActivity::class.java))
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateInput(): Boolean {
        val company = binding.textInputEditTextCompanyNameAdmin.text.toString().trim()
        val date = binding.textInputEditTextDateAdmin.text.toString().trim()
        val description = binding.textInputEditTextDescription.text.toString().trim()
        val link = binding.textInputEditTextLink.text.toString().trim()

        return when {
            company.isEmpty() -> {
                binding.textInputEditTextCompanyNameAdmin.error = "Company name is required"
                false
            }

            date.isEmpty() -> {
                binding.textInputEditTextDateAdmin.error = "Date is required"
                false
            }

            description.isEmpty() -> {
                binding.textInputEditTextDescription.error = "Description is required"
                false
            }

            link.isEmpty() -> {
                binding.textInputEditTextLink.error = "Link is required"
                false
            }

            else -> true
        }
    }

    private fun uploadPlacements() {
        val company = binding.textInputEditTextCompanyNameAdmin.text.toString().trim()
        val date = binding.textInputEditTextDateAdmin.text.toString().trim()
        val description = binding.textInputEditTextDescription.text.toString().trim()
        val link = binding.textInputEditTextLink.text.toString().trim()
        val regLink = binding.textInputEditTextRegLink.text.toString().trim()

        val timestamp = System.currentTimeMillis()

        val placements = hashMapOf(
            "company" to company,
            "date" to date,
            "description" to description,
            "link" to link,
            "regLink" to regLink,
            "timestamp" to timestamp
        )

        db.collection("placements")
            .add(placements)
            .addOnSuccessListener {
                clearInputFields()
                binding.progressBar.visibility = View.GONE
                Snackbar.make(binding.root, "Upload Successful", Snackbar.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                binding.progressBar.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    "Something went wrong: ${exception.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }

    private fun clearInputFields() {
        binding.textInputEditTextCompanyNameAdmin.text?.clear()
        binding.textInputEditTextDateAdmin.text?.clear()
        binding.textInputEditTextDescription.text?.clear()
        binding.textInputEditTextLink.text?.clear()
        binding.textInputEditTextRegLink.text?.clear()
    }

}

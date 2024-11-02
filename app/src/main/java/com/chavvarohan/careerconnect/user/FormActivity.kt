package com.chavvarohan.careerconnect.user

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chavvarohan.careerconnect.databinding.ActivityFormBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FormActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        loadData()

        binding.buttonSave.setOnClickListener {
            saveUserData()
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

    private fun loadData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("registrationData").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        binding.editTextFirstName.setText(document.getString("firstName"))
                        binding.editTextLastName.setText(document.getString("lastName"))
                        binding.editTextDob.setText(document.getString("dob"))  // Assuming you have a field for DOB
                        binding.editTextEmailForm.setText(document.getString("registerEmail"))
                        binding.editTextAddress.setText(document.getString("address"))
                        binding.editTextCity.setText(document.getString("city"))
                        binding.editTextState.setText(document.getString("state"))
                        binding.editTextPostalCode.setText(document.getString("postalCode"))
                        binding.editTextPhoneNumber.setText(document.getString("phoneNumber"))
                    } else {
                        Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error loading data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserData() {
        val firstName = binding.editTextFirstName.text.toString().trim()
        val lastName = binding.editTextLastName.text.toString().trim()
        val dob = binding.editTextDob.text.toString().trim()
        val fullName = "$firstName $lastName"
        val email = binding.editTextEmailForm.text.toString().trim()
        val address = binding.editTextAddress.text.toString().trim()
        val city = binding.editTextCity.text.toString().trim()
        val state = binding.editTextState.text.toString().trim()
        val postalCode = binding.editTextPostalCode.text.toString().trim()
        val phoneNumber = binding.editTextPhoneNumber.text.toString().trim()

        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || dob.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val userData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "dob" to dob,
            "registerEmail" to email,
            "address" to address,
            "city" to city,
            "state" to state,
            "postalCode" to postalCode,
            "phoneNumber" to phoneNumber,
            "email" to email
        )

        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("registrationData").document(userId)
                .set(userData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}

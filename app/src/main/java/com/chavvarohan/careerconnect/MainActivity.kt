package com.chavvarohan.careerconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chavvarohan.careerconnect.admin.AdminActivity
import com.chavvarohan.careerconnect.databinding.ActivityMainBinding
import com.chavvarohan.careerconnect.user.HomeActivity
import com.chavvarohan.careerconnect.user.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Check if user is already signed in and email is verified
        if (auth.currentUser != null && auth.currentUser!!.isEmailVerified) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.buttonSignIn.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            binding.progressBar.visibility = View.VISIBLE

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInUser(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.textViewRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.buttonAdmin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val pass = binding.editTextPassword.text.toString()
            binding.progressBar.visibility = View.VISIBLE

            if (email.isNotEmpty() && pass.trim().isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding.progressBar.visibility = View.GONE
                        val userId = auth.currentUser?.uid
                        Log.d("AuthSuccess", "User ID: $userId")
                        checkAdminRole(userId)
                    } else {
                        Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                        Log.e("AuthError", "Sign-in failed: ${task.exception?.message}")
                    }
                }
            } else {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Empty fields not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        binding.progressBar.visibility = View.GONE
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else if (user != null && !user.isEmailVerified) {
                        Toast.makeText(this, "Please verify your email before signing in.", Toast.LENGTH_SHORT).show()
                        auth.signOut()
                    }
                } else {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkAdminRole(userId: String?) {
        if (userId == null) {
            Toast.makeText(this, "User ID is null", Toast.LENGTH_SHORT).show()
            Log.e("RoleCheck", "User ID is null")
            return
        }

        Log.d("RoleCheck", "Checking role for User ID: $userId")

        firestore.collection("admins").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val role = document.getString("role")
                    Log.d("RoleCheck", "User role: $role")
                    val user = auth.currentUser

                    if (user != null && user.isEmailVerified) {
                        if (role == "admin") {
                            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, AdminActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Access Denied: Not an admin", Toast.LENGTH_SHORT).show()
                            Log.e("RoleCheck", "Access Denied: User is not an admin")
                        }
                    } else if (user != null && !user.isEmailVerified) {
                        Toast.makeText(this, "Please verify your email before signing in.", Toast.LENGTH_SHORT).show()
                        auth.signOut()
                    }
                } else {
                    Toast.makeText(this, "Admin data not found", Toast.LENGTH_SHORT).show()
                    Log.e("RoleCheck", "Admin data not found for User ID: $userId")
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("RoleCheck", "Error fetching user data: ${exception.message}")
            }
    }
}

package com.chavvarohan.careerconnect.admin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chavvarohan.careerconnect.R
import com.chavvarohan.careerconnect.databinding.ActivityTrainingAdminBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.util.UUID

class TrainingAdminActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTrainingAdminBinding

    private lateinit var storageRef: StorageReference

    private var selectedImageUri: Uri? = null

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainingAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageRef = Firebase.storage.reference

        val pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    selectedImageUri = uri
                    binding.imageViewLogo.setImageURI(uri)
                }
            }

        binding.imageViewLogo.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }


        binding.buttonSave.setOnClickListener {
            alertDialog()
        }

        binding.buttonViewUploads.setOnClickListener {
            val intent = Intent(this, ViewTrainingUploadsActivity::class.java)
            startActivity(intent)
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

    private fun alertDialog() {
        AlertDialog.Builder(this@TrainingAdminActivity)
            .setTitle("Upload Hackathon")
            .setMessage("Are you sure you want to upload this hackathon?")
            .setPositiveButton("Yes") { _, _ ->
                binding.progressBar.visibility = View.VISIBLE
                val title = binding.textInputEditTextTitleAdmin.text.toString()
                val date = binding.textInputEditTextDateAdmin.text.toString()
                val description = binding.textInputEditTextDescription.text.toString()
                val link = binding.textInputEditTextLink.text.toString()

                if (title.isEmpty()) {
                    binding.progressBar.visibility = View.GONE
                    binding.textInputEditTextTitleAdmin.error = "Title is required"
                    return@setPositiveButton
                }
                if (date.isEmpty()) {
                    binding.progressBar.visibility = View.GONE
                    binding.textInputEditTextDateAdmin.error = "Date is required"
                    return@setPositiveButton
                }
                if (description.isEmpty()) {
                    binding.progressBar.visibility = View.GONE
                    binding.textInputEditTextDescription.error = "Description is required"
                    return@setPositiveButton
                }
                if (link.isEmpty()) {
                    binding.progressBar.visibility = View.GONE
                    binding.textInputEditTextLink.error = "Link is required"
                    return@setPositiveButton
                }
                uploadImageAndSaveData(title, date, description, link)

            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun uploadImageAndSaveData(title: String, date: String, description: String, link: String) {
        val imageName = UUID.randomUUID().toString()
        val imageRef = storageRef.child("images/$imageName")

        selectedImageUri?.let { uri ->
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        Log.d("FirebaseStorage", "Image uploaded successfully: $downloadUrl")
                        binding.progressBar.visibility = View.GONE
                        saveDataToFirestore(title,date,description,link, downloadUrl.toString())
                    }.addOnFailureListener { exception ->
                        Log.e("FirebaseStorage", "Failed to get download URL: ${exception.message}")
                        Toast.makeText(
                            this,
                            "Failed to get download URL: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FirebaseStorage", "Failed to upload image: ${exception.message}")
                    Toast.makeText(
                        this,
                        "Failed to upload image: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun saveDataToFirestore(
        title: String,
        date: String,
        description: String,
        link: String,
        imageUrl: String
    ) {

        val hackathonData = hashMapOf(
            "title" to title,
            "date" to date,
            "description" to description,
            "link" to link,
            "imageUrl" to imageUrl,
            "timestamp" to System.currentTimeMillis()

        )

        db.collection("training")
            .add(hackathonData)
            .addOnSuccessListener {
                Log.d("Firestore", "Data saved successfully")
                binding.progressBar.visibility = View.GONE
                binding.textInputEditTextTitleAdmin.text?.clear()
                binding.textInputEditTextDateAdmin.text?.clear()
                binding.textInputEditTextDescription.text?.clear()
                binding.textInputEditTextLink.text?.clear()
                binding.imageViewLogo.setImageResource(R.drawable.placeholder_jpg) // Reset image view
                selectedImageUri = null
            }
            .addOnFailureListener {exception->
                Log.d("Firestore", "Error saving data")
                Toast.makeText(
                    this,
                    "Failed to save announcement: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
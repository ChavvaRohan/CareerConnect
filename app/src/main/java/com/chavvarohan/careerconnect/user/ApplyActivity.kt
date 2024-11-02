package com.chavvarohan.careerconnect.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.chavvarohan.careerconnect.databinding.ActivityApplyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ApplyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplyBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val bundle: Bundle? = intent.extras
        val companyName = bundle!!.getString("companyName")
        val date = bundle.getString("date")
        val description = bundle.getString("description")
        val regLink = bundle.getString("regLink")
//        val link = bundle.getString("link")

        binding.textViewCompany.text = companyName
        binding.textViewDate.text = date
        binding.textViewDescription.text = description

        binding.buttonApply.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Apply")
                .setMessage("Are you sure you want to Apply for this selected Placement?")
                .setPositiveButton("yes"){_,_->
                    dataFromFBase()
                    val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(regLink))
                    startActivity(intent)
                }
                .setNegativeButton("No",null)
                .show()

        }
    }

    private fun dataFromFBase() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("registrationData").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    val firstName = document.getString("firstName") ?: "Unknown"
                    val lastName = document.getString("lastName") ?: "User"
                    val email = document.getString("registerEmail") ?: "no-email@example.com"
                    val address = document.getString("address") ?: "No Address"
                    val phone = document.getString("phoneNumber") ?: "No Phone"
                    val pincode = document.getString("postalCode") ?: "No Pincode"
                    val city = document.getString("city") ?: "No City"
                    val state = document.getString("state") ?: "No State"
                    val dob = document.getString("dob") ?: "No DOB"


                    saveData("$firstName $lastName", email, address,phone,pincode,city,state,dob)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Failed to retrieve data: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData(name: String, email: String, address: String,phone:String,pincode:String,city:String,state:String,dob:String) {
        db.collection("placements")
            .get()
            .addOnSuccessListener { document ->
                val link: String = document.documents[0].getString("link")!!

                val url = link + "?action=create&name=$name&email=$email&address=$address&phone=$phone&pincode=$pincode&city=$city&state=$state&dob=$dob"

                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        Toast.makeText(this@ApplyActivity, response, Toast.LENGTH_SHORT).show()
                        binding.buttonApply.visibility = android.view.View.GONE
                    },
                    { error ->
                        Toast.makeText(this@ApplyActivity, error.message, Toast.LENGTH_SHORT).show()
                    })

                val queue = Volley.newRequestQueue(this)
                queue.add(stringRequest)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to retrieve data: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }


    }
}

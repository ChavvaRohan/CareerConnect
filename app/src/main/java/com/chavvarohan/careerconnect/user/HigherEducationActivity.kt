package com.chavvarohan.careerconnect.user

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chavvarohan.careerconnect.databinding.ActivityHigherEducationBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HigherEducationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHigherEducationBinding

    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: Adapter2
    private val trainingList = mutableListOf<Info>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHigherEducationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        // Setting up the RecyclerView
        adapter = Adapter2(trainingList)
        binding.recyclerViewHackathon.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHackathon.adapter = adapter

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
        // Fetch data from Firestore
        fetchHigherEducationData()

    }

    private fun fetchHigherEducationData() {
        firestore.collection("higherEducation")
            .orderBy("timestamp", Query.Direction.DESCENDING) // Ensure this matches the field in Firestore
            .get()
            .addOnSuccessListener { querySnapshot ->
                val hackathonList = ArrayList<Info>()
                for (document in querySnapshot.documents) {
                    val title = document.getString("title") ?: ""
                    val date = document.getString("date") ?: ""
                    val description = document.getString("description") ?: ""
                    val imageUrl = document.getString("imageUrl") ?: ""
                    val link = document.getString("link") ?: ""
                    val timestamp = document.getLong("timestamp") ?: System.currentTimeMillis() // Default value

                    val item = Info(imageUrl, title, date, description, link, timestamp)
                    hackathonList.add(item)

                    // Log each item fetched
                    Log.d("HigherEducationActivity", "Fetched item: $item")
                }

                if (hackathonList.isEmpty()) {
                    Log.d("HigherEducationActivity", "No items found")
                }

                adapter.updateData(hackathonList)

            }
            .addOnFailureListener { exception ->
                Log.e("HigherEducationActivity", "Error fetching data: ", exception)
                Toast.makeText(this, "Failed to fetch data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
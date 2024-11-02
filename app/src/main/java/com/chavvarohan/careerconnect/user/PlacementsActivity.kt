package com.chavvarohan.careerconnect.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chavvarohan.careerconnect.databinding.ActivityPlacementsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query

class PlacementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacementsBinding
    private val db = Firebase.firestore
    private lateinit var adapter: Adapter
    private val placementsList = mutableListOf<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchPlacementsFromFirestore()

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

    private fun setupRecyclerView() {
        binding.recyclerViewPlacements.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(placementsList)
        binding.recyclerViewPlacements.adapter = adapter

        adapter.setOnItemClickListener(object : Adapter.OnItemClickListener {
            override fun onRegisterClick(position: Int) {
                val intent = Intent(this@PlacementsActivity, ApplyActivity::class.java).apply {
                    putExtra("companyName", placementsList[position].companyName)
                    putExtra("date", placementsList[position].date)
                    putExtra("description", placementsList[position].description)
                    putExtra("link", placementsList[position].link)
                    putExtra("regLink", placementsList[position].regLink)
                }
                startActivity(intent)
            }
        })
    }

    private fun fetchPlacementsFromFirestore() {
        db.collection("placements")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                placementsList.clear()  // Clear the list to avoid duplication
                for (document in documents) {
                    val companyName = document.getString("company") ?: ""
                    val date = document.getString("date") ?: ""
                    val description = document.getString("description") ?: ""
                    val link = document.getString("link") ?: ""
                    val timestamp = document.getLong("timestamp") ?: System.currentTimeMillis()
                    val regLink = document.getString("regLink") ?: ""

                    val placement = Data(companyName, description, link, date, regLink, timestamp)
                    placementsList.add(placement)
                }
                // Notify the adapter that data has been updated
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("PlacementsActivity", "Error getting documents: ", exception)
                Snackbar.make(binding.root, "Failed to load data", Snackbar.LENGTH_LONG).show()
            }
    }

}

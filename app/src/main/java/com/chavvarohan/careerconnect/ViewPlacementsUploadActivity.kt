package com.chavvarohan.careerconnect

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chavvarohan.careerconnect.admin.AdminAdapter
import com.chavvarohan.careerconnect.admin.PlacementsAdminActivity
import com.chavvarohan.careerconnect.databinding.ActivityViewPlacementsUploadBinding
import com.chavvarohan.careerconnect.user.Info
import com.google.firebase.firestore.FirebaseFirestore

class ViewPlacementsUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPlacementsUploadBinding

    private lateinit var db: FirebaseFirestore
    private lateinit var rvMain: RecyclerView
    private lateinit var adapter: AdminPlacementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPlacementsUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)


        rvMain = binding.recyclerViewAdminUploads
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.setHasFixedSize(true)


        db = FirebaseFirestore.getInstance()

        adapter = AdminPlacementAdapter(ArrayList())
        rvMain.adapter = adapter

        fetchHigherEducationFromFirestore()

        adapter.onDeleteClick = { item ->
            deleteHackathonFromFirestore(item)
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

    private fun fetchHigherEducationFromFirestore() {
        binding.progressBar.visibility = View.VISIBLE

        db.collection("placements")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val placementsList = ArrayList<PlacementInfo>()
                if (querySnapshot.isEmpty) {
                    Log.d("Firestore", "No internship found.")
                    adapter.updateData(placementsList)
                    binding.emptyStateTextView.visibility =
                        View.VISIBLE
                } else {
                    for (document in querySnapshot.documents) {
                        val company = document.getString("company") ?: ""
                        val description = document.getString("description") ?: ""
                        val date = document.getString("date") ?: ""
                        val link = document.getString("link") ?: ""
                        val item = PlacementInfo(company, date, description, link)
                        placementsList.add(item)
                    }
                    adapter.updateData(placementsList)
                    binding.emptyStateTextView.visibility = View.GONE
                }
                binding.progressBar.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting internship", exception)
                binding.progressBar.visibility = View.GONE
                binding.emptyStateTextView.visibility = View.VISIBLE
            }
    }

    private fun deleteHackathonFromFirestore(item: PlacementInfo) {
        Log.d("Firestore", "Attempting to delete company: ${item.company}")

        db.collection("placements")
            .whereEqualTo("company", item.company)
            .whereEqualTo("description", item.description)
            .whereEqualTo("date", item.date)
            .whereEqualTo("link", item.link)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    Log.d("Firestore", "No documents found to delete.")
                } else {
                    for (document in querySnapshot.documents) {
                        Log.d("Firestore", "Deleting document with ID: ${document.id}")
                        document.reference.delete()
                            .addOnSuccessListener {
                                Log.d("Firestore", "DocumentSnapshot successfully deleted!")
                                fetchHigherEducationFromFirestore() // Refresh the list
                            }
                            .addOnFailureListener { e ->
                                Log.w("Firestore", "Error deleting document", e)
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error finding document", e)
            }
    }


}
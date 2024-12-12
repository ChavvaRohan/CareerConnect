package com.chavvarohan.careerconnect.admin

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chavvarohan.careerconnect.user.Info
import com.chavvarohan.careerconnect.databinding.ActivityViewTrainingUploadsBinding
import com.google.firebase.firestore.FirebaseFirestore

class ViewTrainingUploadsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewTrainingUploadsBinding

    private lateinit var db: FirebaseFirestore
    private lateinit var rvMain: RecyclerView
    private lateinit var adapter: AdminAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewTrainingUploadsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvMain = binding.recyclerViewAdminUploads
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.setHasFixedSize(true)

        db = FirebaseFirestore.getInstance()

        adapter = AdminAdapter(ArrayList())
        rvMain.adapter = adapter

        fetchTrainingsFromFirestore()

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

    private fun fetchTrainingsFromFirestore() {
        binding.progressBar.visibility = View.VISIBLE

        db.collection("training")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val hackathonsList = ArrayList<Info>()
                if (querySnapshot.isEmpty) {
                    Log.d("Firestore", "No trainings found.")
                    adapter.updateData(hackathonsList)
                    binding.emptyStateTextView.visibility = View.VISIBLE
                } else {
                    for (document in querySnapshot.documents) {
                        val title = document.getString("title") ?: ""
                        val description = document.getString("description") ?: ""
                        val imageUrl = document.getString("imageUrl") ?: ""
                        val date = document.getString("date") ?: ""
                        val link = document.getString("link") ?: ""
                        val item = Info(imageUrl, title, date, description, link)
                        hackathonsList.add(item)
                    }
                    adapter.updateData(hackathonsList)
                    binding.emptyStateTextView.visibility = View.GONE
                }
                binding.progressBar.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting trainings", exception)
                binding.progressBar.visibility = View.GONE
                binding.emptyStateTextView.visibility = View.VISIBLE
            }
    }

    private fun deleteHackathonFromFirestore(item: Info) {
        Log.d("Firestore", "Attempting to delete hackathon with title: ${item.title}")

        db.collection("training")
            .whereEqualTo("title", item.title)
            .whereEqualTo("description", item.description)
            .whereEqualTo("imageUrl", item.image)
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
                                fetchTrainingsFromFirestore()
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
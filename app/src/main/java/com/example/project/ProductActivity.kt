package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.log

class ProductActivity : AppCompatActivity() {

    private var adapter: ProductAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recylerview)

        val query = FirebaseDatabase.getInstance().reference.child("cakes")

        val postListener = object : ValueEventListener {
             override fun onDataChange(dataSnapshot: DataSnapshot) {
                 for (cakeSnapshot in dataSnapshot.children) {
                     val cake = cakeSnapshot.getValue(Cakes::class.java)
                     Log.i("ProductActivity", "Cake Name: ${cake?.name}, Description: ${cake?.description}")
                 }
                }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ProductActivity", "Database Error: ${databaseError.message}")
            }
        }
       query.addValueEventListener(postListener)
        val options = FirebaseRecyclerOptions.Builder<Cakes>().setQuery(query, Cakes::class.java).build()
        adapter = ProductAdapter(options)

        val rView: RecyclerView = findViewById(R.id.rView)
        rView.layoutManager = LinearLayoutManager(this)
        rView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()


    }

}
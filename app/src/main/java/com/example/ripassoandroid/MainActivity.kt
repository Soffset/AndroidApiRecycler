package com.example.ripassoandroid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val responseText = findViewById<TextView>(R.id.responseText)
        val usersRecyclerView = findViewById<RecyclerView>(R.id.RecyclerView_Users)

        val adapter = CustomAdapter(arrayOf())
        usersRecyclerView.adapter = adapter
        usersRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        button.setOnClickListener {
            sendJsonRequest (
                onSuccess = {
                    responseText.text = "Success"
                    val users = Gson().fromJson(it, Array<User>::class.java)
                    Log.d("MainActivity", users.toString())
                    usersRecyclerView.adapter = CustomAdapter(users)
                    usersRecyclerView.refreshDrawableState()
                },
                onFail = {
                    responseText.text = "Fail"
                }
            )
        }
    }

    private fun sendJsonRequest(onSuccess: (String) -> Unit = {}, onFail: (String) -> Unit = {}) {

        val url = "https://jsonplaceholder.typicode.com/users"

        val queue = Volley.newRequestQueue(this)

        val jsonRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                run {
                    onSuccess(response.toString())
                }
            },
            { error ->
                run {
                    onFail(error.toString())
                }
            })

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)

    }
}

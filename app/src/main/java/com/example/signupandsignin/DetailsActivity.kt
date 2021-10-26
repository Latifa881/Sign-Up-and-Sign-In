package com.example.signupandsignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DetailsActivity : AppCompatActivity() {
    lateinit var btSignOut: Button
    lateinit var tvName: TextView
    lateinit var tvUsername: TextView
    lateinit var tvLocation: TextView
    lateinit var tvPhone: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        btSignOut = findViewById(R.id.btSignOut)
        tvName = findViewById(R.id.tvName)
        tvUsername = findViewById(R.id.tvUsername)
        tvLocation = findViewById(R.id.tvLocation)
        tvPhone = findViewById(R.id.tvPhone)
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val location = intent.getStringExtra("location")
        val username = intent.getStringExtra("username")
        tvName.setText(name)
        tvPhone.setText(phone)
        tvLocation.setText(location)
        tvUsername.setText(username)

        btSignOut.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
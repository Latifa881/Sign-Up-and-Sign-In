package com.example.signupandsignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var btSignUp:Button
    lateinit var btSignIn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btSignUp=findViewById(R.id.btSignUp)
        btSignIn=findViewById(R.id.btSignIn)


        btSignIn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }
        btSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}
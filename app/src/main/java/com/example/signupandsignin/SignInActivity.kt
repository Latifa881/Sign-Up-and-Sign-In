package com.example.signupandsignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.time.Instant

class SignInActivity : AppCompatActivity() {
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var btSignIn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btSignIn = findViewById(R.id.btSignIn)
        btSignIn.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                val dbHelper = DBHelper(this)
                val users = dbHelper.readData()
                var found=false
                for(i in users){
                    if(i.username.equals(username)){
                        found=true
                     //   Toast.makeText(this,"$password ${decryptedPassword(i.password)}",Toast.LENGTH_SHORT).show()
                       if(password.equals(decryptedPassword(i.password)))
                       {
                           val intent=Intent(this,DetailsActivity::class.java)
                           intent.putExtra("name", i.name)
                           intent.putExtra("phone", i.phone)
                           intent.putExtra("location", i.location)
                           intent.putExtra("username", i.username)
                           startActivity(intent)
                       }else{
                           etPassword.setText("")
                           Toast.makeText(this,"Wrong password",Toast.LENGTH_SHORT).show()
                       }
                        break
                    }
                }
                if(!found){
                    etUsername.setText("")
                    etPassword.setText("")
                    Toast.makeText(this,"Wrong username",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enter a username and password", Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun decryptedPassword(encryptedPassword: String): String {
        var decryptedPass = ""
        for (letter in encryptedPassword) {
            decryptedPass += (letter - 13).toString()
        }
        return decryptedPass
    }
}
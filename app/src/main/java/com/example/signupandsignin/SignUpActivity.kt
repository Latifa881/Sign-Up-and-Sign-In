package com.example.signupandsignin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etPhone: EditText
    lateinit var etLocation: EditText
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var tvErrorMessages: TextView
    lateinit var btSubmit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhone)
        etLocation = findViewById(R.id.etLocation)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        tvErrorMessages = findViewById(R.id.tvErrorMessages)
        btSubmit = findViewById(R.id.btSubmit)

        btSubmit.setOnClickListener {
            // Hide Keyboard
            val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)

            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val location = etLocation.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            var pass = true

            var errorMessage = ""
            if (name.isEmpty()) {
                errorMessage += "Enter a name\n"
                pass = false
            }
            if (phone.length != 10) {
                errorMessage += "Phone number should be 10 digits\n"
                pass = false
            }
            if (location.isEmpty()) {
                errorMessage += "Enter a location\n"
                pass = false
            }
            if (username.isEmpty()) {
                errorMessage += "Enter a username\n"
                pass = false
            }else if(!checkValidUsername(username)){
                errorMessage += "Username is used choose anther one\n"
                pass = false
            }
            if (!isValidPasswordFormat(password)) {
                errorMessage += "Password Conditions:\n" +
                        "- at least 1 digit\n" +
                        "- at least 1 lower case letter\n" +
                        "- at least 1 upper case letter\n" +
                        "- at least 8 characters\n"
                pass = false
            }
            if (!confirmPassword.equals(password)) {
                errorMessage += "Password doesn't match"
                pass = false
            }
            if (pass) {
                val dbHelper = DBHelper(this)
                dbHelper.saveData(
                    User(
                        name,
                        phone,
                        location,
                        username,
                        encryptedPassword(password)
                    )
                )
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("phone", phone)
                intent.putExtra("location", location)
                intent.putExtra("username", username)
                startActivity(intent)
            } else {
                tvErrorMessages.visibility = View.VISIBLE
                tvErrorMessages.setOnClickListener {
                    val dialogBuilder = AlertDialog.Builder(this)

                    // set message of alert dialog
                    dialogBuilder.setMessage(errorMessage)
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // negative button text and action
                        .setNegativeButton("Close", { dialog, id -> dialog.cancel() })

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle("Error Message(s)")
                    // show alert dialog
                    alert.show()

                }
            }

        }
    }


    private fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    ".{8,}" +               //at least 8 characters
                    "$"
        );
        return passwordREGEX.matcher(password).matches()
    }

    private fun checkValidUsername(username: String): Boolean {
        val dbHelper = DBHelper(this)
        val users = dbHelper.readData()
        for (i in users) {
            if (i.username.equals(username))
                return false
        }
        return true
    }

    private fun encryptedPassword(password: String): String {
        var encryptedPass = ""
        for (letter in password) {
            encryptedPass += (letter + 13).toString()
        }
        return encryptedPass
    }




}
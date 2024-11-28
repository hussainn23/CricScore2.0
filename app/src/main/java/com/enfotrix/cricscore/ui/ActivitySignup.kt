package com.enfotrix.cricscore.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.enfotrix.cricscore.R
import com.enfotrix.cricscore.databinding.ActivitySignupBinding
import com.enfotrix.cricscore.models.UserModel
import com.enfotrix.cricscore.viewmodel.CricViewModel

class ActivitySignup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private var userType: String = ""
    private lateinit var viewModel: CricViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(CricViewModel::class.java)
        val userTypes = listOf("user", "player", "coach")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerUserType.adapter = adapter
        binding.spinnerUserType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                userType = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.btnSignup.setOnClickListener {

                submitRegistration()

        }
    }

    private fun submitRegistration() {
        val firstName = binding.etFirstName.text.toString().trim()
        val secondName = binding.etSecondName.text.toString().trim()
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (firstName.isEmpty()) {
            showToast("First Name is required")
            return
        }
        if (secondName.isEmpty()) {
            showToast("Second Name is required")
            return
        }
        if (phoneNumber.isEmpty() || !phoneNumber.matches("\\d{11}".toRegex())) {
            showToast("Valid Phone Number is required")
            return
        }
        if (password.isEmpty() || password.length < 6) {
            showToast("Password must be at least 6 characters")
            return
        }
        if (password != confirmPassword) {
            showToast("Passwords do not match")
            return
        }
        if (userType.equals("")) {
            showToast("User type is required")
            return
        }
        val user = UserModel(
             firstName,
             secondName,
             phoneNumber,
             password,
             userType
        )
        viewModel.users.observe(this) { users ->
            if (users != null) {
                if (users.any { it.phoneNumber == phoneNumber }) {
                    showToast("User already exists")
                } else {
                    viewModel.registerUser(user)
                }
            }
        }

        viewModel.registrationResult.observe(this) { message ->
            showToast(message)
            if (message == "Registration successful") {
                startActivity(Intent(this, ActivityLogin::class.java))
                finish()
            }
        }




    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
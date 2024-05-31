package com.pops.z_gaming.ActivitiesLogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pops.z_gaming.ActivitiesLogin.SendEmail
import com.pops.z_gaming.MainActivity
import com.pops.z_gaming.databinding.FragmentLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        addListeners()
    }

    fun addListeners() {
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        binding.txtPasswordForgotten.setOnClickListener {
            val intent = Intent(this, SendEmail::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
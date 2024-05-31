package com.pops.z_gaming.ActivitiesLogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pops.z_gaming.ActivitiesLogin.Login
import com.pops.z_gaming.SessionManager
import com.pops.z_gaming.databinding.FragmentLoginBinding
import com.pops.z_gaming.databinding.FragmentSignupBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: FragmentSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignupBinding.inflate(layoutInflater)

        setContentView(binding.root)
        addListeners()
        showToken()
    }

    fun addListeners(){
        binding.btnReturnLogin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener{
            Toast.makeText(this, "Cuenta registrada con éxito", Toast.LENGTH_LONG).show()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    fun showToken(){
        Log.i("LOGIN_T", "TOKEEEN: ${SessionManager.getToken()}")
        Log.i("LOGIN_T", "USER_SIGNUP: ${SessionManager.getUser()}")
    }
}
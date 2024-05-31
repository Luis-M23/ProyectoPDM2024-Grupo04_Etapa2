package com.pops.z_gaming.ActivitiesLogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pops.z_gaming.ActivitiesLogin.Login
import com.pops.z_gaming.databinding.FragmentRestorePasswordBinding

class RestorePassword : AppCompatActivity() {
    private lateinit var binding: FragmentRestorePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRestorePasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)
        addListeners()
    }

    private fun addListeners() {
        binding.btnNewPasswordConfirmed.setOnClickListener {
            Toast.makeText(this, "Contraseña restaurada", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}
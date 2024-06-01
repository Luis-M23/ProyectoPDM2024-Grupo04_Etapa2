package com.pops.z_gaming.ActivitiesLogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pops.z_gaming.ActivitiesLogin.Login
import com.pops.z_gaming.Model.UserInsert
import com.pops.z_gaming.RetrofitClient
import com.pops.z_gaming.SessionManager
import com.pops.z_gaming.WebService
import com.pops.z_gaming.databinding.FragmentLoginBinding
import com.pops.z_gaming.databinding.FragmentSignupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class SignUp : AppCompatActivity() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var retrofit: Retrofit

    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String
    private lateinit var phoneText: String
    private var phoneNumber: Int? = null
    private val idRol = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignupBinding.inflate(layoutInflater)
        retrofit = RetrofitClient.getRetrofit()

        setContentView(binding.root)
        addListeners()
    }

    fun addListeners() {
        binding.btnReturnLogin.setOnClickListener {

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {

            registerUser()

        }
    }

    fun registerUser() {
        Log.i("LOGIN_T", "TOKEEEN: ${SessionManager.getToken()}")
        Log.i("LOGIN_T", "USER_SIGNUP: ${SessionManager.getUser()}")

        username = binding.txtUsername.text.toString()
        email = binding.txtMail.text.toString()
        password = binding.txtPassword.text.toString()
        name = binding.txtName.text.toString()
        phoneText = binding.txtPhone.text.toString()

        when {

            name.isEmpty() -> {
                binding.txtName.error = "Este campo es obligatorio"
                binding.txtName.requestFocus()
            }

            phoneText.isEmpty() -> {
                binding.txtPhone.error = "Este campo es obligatorio"
                binding.txtPhone.requestFocus()
            }

            email.isEmpty() -> {
                binding.txtMail.error = "Este campo es obligatorio"
                binding.txtMail.requestFocus()
            }

            username.isEmpty() -> {
                binding.txtUsername.error = "Este campo es obligatorio"
                binding.txtUsername.requestFocus()
            }

            password.isEmpty() -> {
                binding.txtPassword.error = "Este campo es obligatorio"
                binding.txtPassword.requestFocus()
            }

            else -> {
                // Si todos los campos están completos, convertir phoneText a Int
                val phoneNumber = phoneText.toIntOrNull()
                if (phoneNumber == null) {
                    binding.txtPhone.error = "Número de teléfono inválido"
                    binding.txtPhone.requestFocus()
                } else {

                    //Método RETROFIT

                    var usuario = UserInsert(
                        username,
                        email,
                        password,
                        name,
                        phoneNumber,
                        idRol
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        val call = retrofit.create(WebService::class.java).insertarUsuario(usuario)
                        val userReturned = call.body()

                        withContext(Dispatchers.Main) {
                            if (call.isSuccessful) {

                                Toast.makeText(
                                    applicationContext,
                                    "Usuario registrado exitosamente",
                                    Toast.LENGTH_SHORT
                                ).show()

                                //Ir al otro formulario
                                val intent = Intent(applicationContext, Login::class.java)
                                startActivity(intent)

                                //Imprimir el resultado del POST
                                if (userReturned != null) {
                                    Log.i(
                                        "LOGIN_T",
                                        "SIGNUP, user: ${userReturned.userNewResponse}, tk: ${userReturned.token}"
                                    )
                                }

                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Error al Crear usuario. ${call.message()}",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                                Log.i("LOGIN_T", "SIGNUP, ERROR: ${call.message()}")
                            }
                        }
                    }
                }
            }
        }
    }
}
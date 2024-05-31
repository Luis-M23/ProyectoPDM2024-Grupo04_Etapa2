package com.pops.z_gaming.ActivitiesLogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pops.z_gaming.ActivitiesLogin.SendEmail
import com.pops.z_gaming.AppConstantes
import com.pops.z_gaming.MainActivity
import com.pops.z_gaming.Model.UserLogin
import com.pops.z_gaming.Model.Usuario
import com.pops.z_gaming.RetrofitClient
import com.pops.z_gaming.SessionManager
import com.pops.z_gaming.WebService
import com.pops.z_gaming.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Cookie
import okhttp3.Dispatcher
import retrofit2.Retrofit

class Login : AppCompatActivity() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        retrofit = RetrofitClient.getRetrofit()

        setContentView(binding.root)
        addListeners()
    }

    fun login() {

        var user = binding.txtMail.text.toString()
        var pass = binding.txtPassword.text.toString()

        var usuario = UserLogin(
            user,
            pass
        )

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(WebService::class.java).iniciarSesion(usuario)
            val userReturned = call.body()

            val tk = call.headers().get("Set-Cookie").toString()

            val tokenTrue = extractToken(tk)

            if (tokenTrue != null && userReturned != null) {
                SessionManager.setSession(tokenTrue, userReturned)
            }

            withContext(Dispatchers.Main) {
                if (call.isSuccessful && userReturned != null) {
                    Toast.makeText(applicationContext, "Logeado exitosamente", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Error al logearse", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            Log.i("LOGIN_T", "$userReturned")
            Log.i("LOGIN_T", "$tk")
            Log.i("LOGIN_T", "TOKEN: ${SessionManager.getToken()}")
            Log.i("LOGIN_T", "USER: ${SessionManager.getUser()}")
        }
    }

    fun extractToken(authString: String): String? {
        val tokenPrefix = "token="
        val delimiter = ";"

        // Encuentra el índice del prefijo "token="
        val startIndex = authString.indexOf(tokenPrefix)
        if (startIndex == -1) {
            // Prefijo no encontrado
            return null
        }

        // Calcula el índice de inicio real del token
        val tokenStartIndex = startIndex + tokenPrefix.length

        // Encuentra el índice del delimitador ";"
        val endIndex = authString.indexOf(delimiter, tokenStartIndex)
        return if (endIndex == -1) {
            // Si no se encuentra el delimitador, devuelve el resto de la cadena
            authString.substring(tokenStartIndex)
        } else {
            // Extrae el token entre el prefijo y el delimitador
            authString.substring(tokenStartIndex, endIndex)
        }
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
            login()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
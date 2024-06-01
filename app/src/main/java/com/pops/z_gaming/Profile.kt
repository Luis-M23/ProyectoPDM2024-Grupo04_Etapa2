package com.pops.z_gaming

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pops.z_gaming.databinding.FragmentHomeBinding
import com.pops.z_gaming.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var retrofit: Retrofit



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserData()
    }

    private fun setUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val idUser = SessionManager.getUser()?.idUsuario
            if(idUser != null){
                retrofit = RetrofitClient.getRetrofit()
                val call = retrofit.create(WebService::class.java).obtenerUsuario(idUser)
                val user = call.body() //Obtener el cuerpo del GET

                if (call.isSuccessful) {
                    Log.i("LOGIN_T", "PROFILE, FUNCA")
                    if (user != null) {
                        Log.i("LOGIN_T", "NOMBRE, ${user.nombre}")
                        Log.i("LOGIN_T", "TELEFONO, ${user.numeroTelefono}")

                        //Modificar elementos en hilo principal
                        withContext(Dispatchers.Main) {
                            binding.editTextName.setText(user.nombre)
                            binding.editTextPhone.setText(user.numeroTelefono)
                            binding.textviewNameUser.text = user.nombreUsuario
                            binding.textviewEmail.text = user.correo
                        }
                    }
                } else {
                    Log.i("LOGIN_T", "PROFILE, FALLO ${call.message()}")
                }
            }else{
                Log.i("LOGIN_T", "PROFILE, USER IS NULL")
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
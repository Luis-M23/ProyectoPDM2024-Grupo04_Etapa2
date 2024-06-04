package com.pops.z_gaming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.Model.UserInsert
import com.pops.z_gaming.Model.UserRolRequest
import com.pops.z_gaming.Model.UserStateRequest
import com.pops.z_gaming.Model.Usuario
import com.pops.z_gaming.databinding.FragmentUserManagementBinding
import com.pops.z_gaming.rv_adapter.userManagement.UserManagementAdapter
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
 * Use the [UserManagerAdmin.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserManagerAdmin : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentUserManagementBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvUserManagement: RecyclerView
    private lateinit var userAdapter: UserManagementAdapter

    private lateinit var retrofit: Retrofit
    private var userList = mutableListOf<Usuario>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        _binding = FragmentUserManagementBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Listeners
        Log.i("LOGIN_T", "USERMANAGEMENT, Antes Initrecycler")
        getAllUsers()

        binding.returnIcon2.setOnClickListener {
            val intent = Intent(requireContext(), MainActivityAdmin::class.java)
            startActivity(intent)
        }
        binding.btnAll.setOnClickListener { getAllUsers() }
        binding.btnAdmin.setOnClickListener { getUsersByRole(2) }
        binding.btnUser.setOnClickListener { getUsersByRole(1) }
    }

    fun initRecyclerView() {
        Log.i("LOGIN_T", "USERMANAGEMENT, Initrecycler")
        rvUserManagement = binding.rvUserManagement
        rvUserManagement.layoutManager = LinearLayoutManager(requireContext())

        userAdapter = UserManagementAdapter(userList,
            { product, idUserInViewHolder -> updateUserState(product, idUserInViewHolder) },
            { product, idUserInViewHolder -> updateUserRole(product, idUserInViewHolder) })
        rvUserManagement.adapter = userAdapter
    }

    private fun updateUserRole(user: Usuario, idUserInViewHolder: Int) {

        Log.i("LOGIN_T", "ROLE Actualizar un producto con id: ${user.idUsuario}")
        Log.i("LOGIN_T", "ROLE Actualizar a un recycler con id: $idUserInViewHolder")

        val dialogView = layoutInflater.inflate(R.layout.fragment_dialog_delete_product_confirmation, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val messageTextTitle = dialogView.findViewById<TextView>(R.id.dialog_delete_title)
        messageTextTitle.text = "Modificar rol del usuario"

        val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_delete_message)
        messageTextView.text = "¿Seguro de modificar el rol del usuario?"

        val messageButton = dialogView.findViewById<TextView>(R.id.btnConfirmed)
        messageButton.text = "Aceptar"

        val cancelButton = dialogView.findViewById<Button>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        val confirmedButton = dialogView.findViewById<Button>(R.id.btnConfirmed)

        confirmedButton.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {

                val newUserRol = UserRolRequest(user.idRol)

                Log.i("LOGIN_T", "EDITUSERSROLE, EDITUSERSROLE, id: ${user.idUsuario}, newRol: $newUserRol")

                val call =
                    retrofit.create(WebService::class.java)
                        .actualizarRolDeUsuario(user.idUsuario, newUserRol)
                val userReturned = call.body()

                Log.i("LOGIN_T", "EDITUSERSROLE, response: ${userReturned}")

                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Rol del usuario actualizado satisfactoriamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.rvUserManagement.adapter?.notifyItemChanged(idUserInViewHolder)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al actualizar el rol del usuario ${call.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.i("LOGIN_T", "ADDPRODUCT, ERROr: ${call.message()}")
                    }
                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun updateUserState(user: Usuario, idUserInViewHolder: Int) {

        Log.i("LOGIN_T", "STATE Actualizar un producto con id: ${user.idUsuario}")
        Log.i("LOGIN_T", "STATE Actualizar a un recycler con id: $idUserInViewHolder")

        val dialogView = layoutInflater.inflate(R.layout.fragment_dialog_delete_product_confirmation, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val messageTextTitle = dialogView.findViewById<TextView>(R.id.dialog_delete_title)
        messageTextTitle.text = "Modificar estado del usuario"

        val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_delete_message)
        messageTextView.text = "¿Seguro de modificar el estado del usuario?"

        val messageButton = dialogView.findViewById<TextView>(R.id.btnConfirmed)
        messageButton.text = "Aceptar"

        val cancelButton = dialogView.findViewById<Button>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        val confirmedButton = dialogView.findViewById<Button>(R.id.btnConfirmed)
        confirmedButton.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {

                val newUserState = UserStateRequest(user.activo!!)
                val call =
                    retrofit.create(WebService::class.java)
                        .actualizarEstadoDeUsuario(user.idUsuario, newUserState)
                val userReturned = call.body()

                Log.i("LOGIN_T", "EDITUSERSTATE, response: ${userReturned}")

                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Estado del usuario actualizado satisfactoriamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.rvUserManagement.adapter?.notifyItemChanged(idUserInViewHolder)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al actualizar el estado del usuario ${call.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.i("LOGIN_T", "UPDATEUSERSTATE, ERROr: ${call.message()}")
                    }
                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    fun getAllUsers() {

        userList = mutableListOf()

        CoroutineScope(Dispatchers.IO).launch {
            retrofit = RetrofitClient.getRetrofit()

            val call = retrofit.create(WebService::class.java).obtenerTodosLosUsuarios()
            val users = call.body() //Obtener el cuerpo del GET

            Log.i("LOGIN_T", "USERMANAGEMENT, Respuesta: ")

            if (call.isSuccessful) {

                withContext(Dispatchers.Main) {
                    if (users != null) {
                        userList.clear() //Limpiar el arreglo
                        for (p in users) {
                            Log.i("LOGIN_T", "User: $p ")
                            userList.add(p)
                        }
                    }
                    Log.i("LOGIN_T", "UserList: $userList")
                    initRecyclerView()
                }
            } else {
                Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT)
                    .show();
            }
        }
    }

    private fun getUsersByRole(roleId: Int) {
        userList = mutableListOf()

        CoroutineScope(Dispatchers.IO).launch {
            retrofit = RetrofitClient.getRetrofit()

            val call = retrofit.create(WebService::class.java).obtenerUsuariosPorRol(roleId)
            val users = call.body()

            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    if (users != null) {
                        userList.clear()
                        userList.addAll(users)
                    }
                    initRecyclerView()
                }
            } else {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT)
                        .show()
                }
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
         * @return A new instance of fragment UserManagerAdmin.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserManagerAdmin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.pops.z_gaming.rv_adapter.userManagement

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.Model.Usuario
import com.pops.z_gaming.databinding.UsersItemsBinding

class UserManagementViewHolder(view: View,
    private val onModifyUserStateClick: (user: Usuario, idUserModifiedOnRecycler: Int) -> Unit) : RecyclerView.ViewHolder(view) {
    private val binding = UsersItemsBinding.bind(view)

    fun bind(user: Usuario) {

        val rol = setRol(user.idRol)

        binding.txtNameUser.text = user.nombre
        binding.txtRol.text = setRol(user.idRol)
        //binding.btnRol.setCompoundDrawablesRelativeWithIntrinsicBounds(user.imageResource, 0, 0, 0)
        binding.btnActive.text = user.activo?.let { isUserActiveToText(it) }

        binding.btnActive.setOnClickListener {
            val userActualState = user.activo

            Log.i("LOGIN_T", "UserManViewholder, new userState: $user")

            if(userActualState != null){
                user.activo = !(userActualState)
                onModifyUserStateClick(user, bindingAdapterPosition)
            }
        }

        // Configurar el color del botón según el rol
        binding.btnActive.backgroundTintList = if (user.activo!!) ColorStateList.valueOf(Color.parseColor("#7BD8B6")) else
            ColorStateList.valueOf(Color.parseColor("#F44336"))
    }

    private fun setRol(idRol: Int): String {
        return if(idRol == 1){
            "User"
        }else{
            "Admin"
        }
    }

    private fun isUserActiveToText(active: Boolean): String = if (active) "Activo" else "Inactivo"

}
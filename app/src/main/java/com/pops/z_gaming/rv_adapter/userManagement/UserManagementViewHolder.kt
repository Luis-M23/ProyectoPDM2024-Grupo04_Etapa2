package com.pops.z_gaming.rv_adapter.userManagement

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.Model.Usuario
import com.pops.z_gaming.databinding.UsersItemsBinding

class UserManagementViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = UsersItemsBinding.bind(view)

    fun bind(user: Usuario) {

        val rol = setRol(user.idRol)

        binding.txtNameUser.text = user.nombre
        binding.txtRol.text = setRol(user.idRol)
        //binding.btnRol.setCompoundDrawablesRelativeWithIntrinsicBounds(user.imageResource, 0, 0, 0)
        binding.btnRol.text = user.activo?.let { isUserActiveToText(it) }

        // Configurar el color del botón según el rol
        binding.btnRol.backgroundTintList = if (user.activo!!) ColorStateList.valueOf(Color.parseColor("#7BD8B6")) else
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
package com.pops.z_gaming.rv_adapter.userManagement

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.R
import com.pops.z_gaming.User
import com.pops.z_gaming.databinding.FragmentUserManagementBinding
import com.pops.z_gaming.databinding.UsersItemsBinding

class UserManagementViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = UsersItemsBinding.bind(view)

    fun bind(user: User) {
        binding.txtNameUser.text = user.name
        binding.txtRol.text = user.role
        //binding.btnRol.setCompoundDrawablesRelativeWithIntrinsicBounds(user.imageResource, 0, 0, 0)
        binding.btnRol.text = isUserActiveToText(user.isActive)

        // Configurar el color del botón según el rol
        binding.btnRol.backgroundTintList = if (user.role == "Admin") ColorStateList.valueOf(Color.parseColor("#7BD8B6")) else
                ColorStateList.valueOf(Color.parseColor("#F44336"))
    }

    private fun isUserActiveToText(active: Boolean): String = if (active) "Activo" else "Inactivo"

}
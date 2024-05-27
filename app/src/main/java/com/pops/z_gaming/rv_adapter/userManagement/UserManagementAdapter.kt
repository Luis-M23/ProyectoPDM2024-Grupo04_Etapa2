package com.pops.z_gaming.rv_adapter.userManagement

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.R
class UserManagementAdapter (private val userList: List<User>) :
    RecyclerView.Adapter<UserManagementAdapter.UserManagementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserManagementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_items, parent, false)
        return UserManagementViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserManagementViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserManagementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageUser: ImageView = itemView.findViewById(R.id.imageUser)
        private val txtNameUser: TextView = itemView.findViewById(R.id.txtNameUser)
        private val txtRol: TextView = itemView.findViewById(R.id.txtRol)
        private val btnRol: Button = itemView.findViewById(R.id.btnRol)

        fun bind(user: User) {
            txtNameUser.text = user.name
            txtRol.text = user.role
            imageUser.setImageResource(user.imageResource)

            // Configurar el color del botón según el rol
            btnRol.backgroundTint = if (user.role == "Admin") Color.parseColor("#7BD8B6") else Color.parseColor("#F44336")
        }
    }
}
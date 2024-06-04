package com.pops.z_gaming.rv_adapter.userManagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.R
import com.pops.z_gaming.Model.Usuario

class UserManagementAdapter (private val userList: List<Usuario>,
                             private val onModifyUserStateClick: (user: Usuario, idUserModifiedOnRecycler: Int) -> Unit) :
    RecyclerView.Adapter<UserManagementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserManagementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_items, parent, false)
        return UserManagementViewHolder(view, onModifyUserStateClick)
    }

    override fun onBindViewHolder(holder: UserManagementViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
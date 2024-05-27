package com.pops.z_gaming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.databinding.FragmentUserManagementBinding
import com.pops.z_gaming.rv_adapter.userManagement.UserManagementAdapter

class UserManagement: AppCompatActivity() {
    private lateinit var rvUserManagement: RecyclerView
    private lateinit var userAdapter: UserManagementAdapter
    private val userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_user_management)

        rvUserManagement = findViewById(R.id.rvUserManagement)
        rvUserManagement.layoutManager = LinearLayoutManager(this)

        // Agrega usuarios de ejemplo a la lista
        userList.add(User("Kevin Grande", "Admin", R.drawable.profile_image_placeholder, true))
        userList.add(User("John Doe", "User", R.drawable.profile_image_placeholder, false))
        userList.add(User("Jane Smith", "Admin", R.drawable.profile_image_placeholder, true))

        userAdapter = UserManagementAdapter(userList)
        rvUserManagement.adapter = userAdapter
    }
}

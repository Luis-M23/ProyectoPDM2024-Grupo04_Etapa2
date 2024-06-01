package com.pops.z_gaming.Model

data class UserInsert(
    val username: String,
    val email: String,
    val password: String,
    val name: String,
    val phoneNumber: Int,
    val idRol: Int
)
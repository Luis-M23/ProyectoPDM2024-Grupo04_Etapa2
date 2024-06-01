package com.pops.z_gaming.Model

data class UserNewResponse(
    val activo: Boolean,
    val contrasenia: String,
    val correo: String,
    val idRol: Int,
    val idUsuario: Int,
    val nombre: String,
    val nombreUsuario: String,
    val numeroTelefono: String
)
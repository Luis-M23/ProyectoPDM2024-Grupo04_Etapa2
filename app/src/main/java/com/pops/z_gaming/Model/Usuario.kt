package com.pops.z_gaming.Model

import com.google.gson.annotations.SerializedName

data class Usuario(
    var idUsuario: Int,
    var contrasenia: String,
    var correo: String,
    var nombre: String,
    var numeroTelefono: String,
    var nombreUsuario: String,
    var activo: Boolean?,
    var idRol: Int
)
package com.pops.z_gaming.Model

// Datos para la solicitud de agregar a favoritos
data class FavoriteRequest(
    val idUsuarioProducto: Int,
    val idUsuario: Int,
    val idProducto: Int
)

// Respuesta de agregar a favoritos
data class FavoriteResponse(
    val message: String,
    val usuarioProducto: UsuarioProducto
)

data class UsuarioProducto(
    val idUsuario: Int,
    val idProducto: Int
)
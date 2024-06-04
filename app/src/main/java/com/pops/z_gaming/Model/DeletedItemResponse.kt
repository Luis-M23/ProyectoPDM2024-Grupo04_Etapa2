package com.pops.z_gaming.Model

import java.io.Serializable

data class DeletedItemResponse(
    val idUsuarioProducto: Int,
    val idUsuario: Int,
    val idProducto: Int
):Serializable{

}

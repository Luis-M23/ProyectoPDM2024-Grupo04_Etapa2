package com.pops.z_gaming.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class InsertProduct(
    var nombreProducto: String?,
    var descripcion: String?,
    var precio: Double?,
    var stock: Int?,
    var imagenProducto: String?,
    var isFavorito: Boolean?,
    var idCategoria: Int?,
    var isAddedInCart: Boolean?
): Serializable {

}
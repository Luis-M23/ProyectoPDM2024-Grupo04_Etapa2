package com.pops.z_gaming.Model

import android.provider.ContactsContract.CommonDataKinds.Photo

//data class FavoriteProduct (
////    val usuarioProducto: UsuarioProducto,
////    val producto: Producto
//)

data class FavoriteProduct(
    val idUsuarioProducto: Long,
    val idUsuario: Long,
    val idProducto: Long?,
    val producto: Producto
)

data class Producto(
    val idProducto: Long,
    val nombreProducto: String,
    val descripcion: String,
    val precio: String,
    val stock: Long,
    val imagenProducto: String,
    val isFavorito: Boolean,
    val isAddedInCart: Any?,
    val idCategoria: Long
)

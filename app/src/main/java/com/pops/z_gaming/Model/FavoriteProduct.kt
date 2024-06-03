package com.pops.z_gaming.Model

import android.provider.ContactsContract.CommonDataKinds.Photo

data class FavoriteProduct (
    val idUsuarioProducto: Int,
    val idUsuario: Int,
    val idProducto: Int,
    val name: String,
    val description: String,
    val price: Double,
    val photo: String
)
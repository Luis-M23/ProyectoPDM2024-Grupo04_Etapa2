package com.pops.z_gaming.Model

import android.provider.ContactsContract.CommonDataKinds.Photo

data class FavoriteProduct (
    val idUsuarioProducto: Int,  // El ID de la relación entre usuario y producto
    val idUsuario: Int,          // El ID del usuario relacionado
    val idProducto: Int,         // El ID del producto relacionado
    val name: String,            // El nombre del producto
    val description: String,     // La descripción del producto
    val price: Double,           // El precio del producto
    val photo: String            // La URL de la foto del producto
)
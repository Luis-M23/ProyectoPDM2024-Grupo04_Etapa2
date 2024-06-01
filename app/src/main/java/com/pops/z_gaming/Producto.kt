package com.pops.z_gaming

data class Producto(
    val idProducto: Int,
    val nombreProducto: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagenProducto: String,
    val isFavorito: Boolean,
    val idCategoria: Int
)
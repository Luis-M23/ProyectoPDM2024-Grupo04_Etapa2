package com.pops.z_gaming

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

//conexion  a la API de forma local
object AppConstantes {
    const val BASE_URL = "http://10.0.2.2:3000/api/"
}

interface WebService {

    //Obtener lista de productos
    @GET("products")
    suspend fun obtenerProductos(): List<Producto>

    //Obtener producto por ID
    @GET("/producto/{idProducto}")
    suspend fun obtenerProducto(
        @Path("idProducto") idProducto: Int
    ): Response<Producto>

    //Obtener productos por categoria
    @GET("/productos/categoria/{idCategoria}")
    suspend fun obtenerProductosPorCategoria(
        @Path("idCategoria") idCategoria: Int
    ): Response<List<Producto>>

    //Agregar producto
    @POST("/producto/add")
    suspend fun agregarProducto(
        @Body producto: Producto
    ): Response<String>

    //Actualizar producto
    @PUT("/producto/update/{idProducto}")
    suspend fun actualizarProducto(
        @Path("idProducto") idProducto: Int,
        @Body producto: Producto
    ): Response<String>

    //Obtener productos favoritos
    @GET("/productos/favoritos")
    suspend fun obtenerProductosFavoritos(): Response<List<Producto>>

    //Eliminar producto por ID
    @DELETE("/producto/delete/{idProducto}")
    suspend fun borrarProducto(
        @Path("idProducto") idProducto: Int
    ): Response<String>
}

//configuración de retrofit
object RetrofitClient {
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WebService::class.java)
    }
}

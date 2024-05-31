package com.pops.z_gaming

import com.google.gson.GsonBuilder
import com.pops.z_gaming.AppConstantes.BASE_URL
import com.pops.z_gaming.Model.User
import com.pops.z_gaming.Model.UserLogin
import com.pops.z_gaming.Model.Usuario
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.net.URL

//conexion  a la API de forma local
object AppConstantes {
    const val BASE_URL = "http://10.0.2.2:3000/api/"
}

object SessionManager {
    private var token: String = "Sin token"
    private var user: Usuario? = null

    fun setSession(newToken: String, newUser: Usuario){
        this.token = newToken
        this.user = newUser
    }

    fun getToken(): String {
        return token
    }

    fun clearToken() {
        token = "Sin token"
    }

    fun getUser(): Usuario? {
        return this.user
    }
}

public interface WebService {

    @POST("/api/login")
    suspend fun iniciarSesion(
        @Body user: UserLogin
    ): Response<Usuario>

    //Obtener lista de productos
    @GET("/productos")
    suspend fun obtenerProductos(): Response<List<Producto>>

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
class RetrofitClient {
    /**
     * Este método retorna la conexión con Retrofit
     */
    companion object{
        @JvmStatic
        fun getRetrofit() : Retrofit{
            return Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

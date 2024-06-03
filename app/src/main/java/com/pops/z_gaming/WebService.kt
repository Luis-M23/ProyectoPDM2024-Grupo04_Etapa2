package com.pops.z_gaming
import android.util.Log
import com.pops.z_gaming.AppConstantes.BASE_URL
import com.pops.z_gaming.Model.FavoriteProduct
import com.pops.z_gaming.Model.FavoriteRequest
import com.pops.z_gaming.Model.FavoriteResponse
import com.pops.z_gaming.Model.InsertProduct
import com.pops.z_gaming.Model.User
import com.pops.z_gaming.Model.UserInsert
import com.pops.z_gaming.Model.UserInsertResponse
import com.pops.z_gaming.Model.UserLogin
import com.pops.z_gaming.Model.Usuario
import com.pops.z_gaming.Retrofit.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


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

    fun setSession(newToken: String){
        this.token = newToken
    }

    fun setSession(newUser: Usuario){
        this.user = newUser
    }

    fun getToken(): String {
        return token
    }

    fun clearSession() {
        token = "Sin token"
        user = null
    }

    fun getUser(): Usuario? {
        return this.user
    }
}

public interface WebService {

    @POST("login")
    suspend fun iniciarSesion(
        @Body user: UserLogin
    ): Response<Usuario>

    @GET("profile/{id}")
    suspend fun obtenerUsuario(
        @Path("id") id: Int
    ): Response<Usuario>

    @POST("/api/register")
    suspend fun insertarUsuario(
        @Body user: UserInsert
    ): Response<UserInsertResponse>

    //Obtener lista de productos
    @GET("products")
    suspend fun obtenerProductos(): List<Producto>

    //Obtener producto por ID
    @GET("/producto/{idProducto}")
    suspend fun obtenerProducto(
        @Path("idProducto") idProducto: Int
    ): Response<Producto>

    //Obtener productos por categoria
    @GET("products/category/{idCategoria}")
    suspend fun obtenerProductosPorCategoria(
        @Path("idCategoria") idCategoria: Int
    ): List<Producto>

    //Agregar producto
    @POST("/api/add-product")
    suspend fun agregarProducto(
        @Body producto: InsertProduct
    ): Response<Producto>

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

    // Agregar producto a favoritos
    @POST("/api/favorites")
    suspend fun addToFavorites(
        @Body favoriteRequest: FavoriteRequest
    ): Response<FavoriteResponse>


    // Agregar producto a favoritos
    @DELETE("/api/favorite/{id}")
    suspend fun removefromFavorites(
        @Path ("id") id:Int
    ): Response<FavoriteResponse>


    @GET("/api/favorites/{idUsuario}")
    suspend fun obtenerFavoritosPorUsuario(
        @Path("idUsuario") idUsuario: Long
    ): Response<List<FavoriteProduct>>
}

//configuración de retrofit
class RetrofitClient {
    /**
     * Este método retorna la conexión con Retrofit
     */
    companion object{
        @JvmStatic
        fun getRetrofit() : Retrofit{

            Log.i("LOGIN_T", "WEBSERVICE, TOKEN ${SessionManager.getToken()}")

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(SessionManager.getToken()))
                .build()

            return Retrofit
                .Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

package pe.edu.cibertec.repository

import okhttp3.OkHttpClient
import pe.edu.cibertec.dto.LoginReq
import pe.edu.cibertec.dto.LoginRes
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object UserRepository {
    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://unusual-tights-foal.cyclic.app/api/v0.1/")
        .addConverterFactory(GsonConverterFactory.create())
//        .client(getClient())

    interface RemoteService{

        @POST("user/login")
        suspend fun login(@Body loginReq: LoginReq): Response<LoginRes>

        /*@GET("product/categories/{des}")
        suspend fun getProductsByCategory(@Path("des") description: String): Response<List<Product>>

        @GET("category")
        suspend fun getCategories(): Response<List<Category>>

        @GET("product/find/{name}")
        suspend fun findProductsByName(@Path("name") nameProduct: String): Response<List<Product>>

        @POST("card/pay")
        suspend fun pay(@Body payReq: PayReq): Response<PayRes>

        //@Headers("Authorization: ")
        @POST("user/signin")
        suspend fun signin(@Body signinReq: SigninReq): Response<SigninRes>

        //@Headers("Authorization: ")
        @POST("user/signup")
        suspend fun signup(@Body signupReq: SignupReq): Response<SignupRes>

        @GET("user/detail")
        suspend fun detailOfUser(): Response<DetailOfUserRes>

        @GET("order/user")
        suspend fun listOrdersByUser(): Response<List<Order>>*/

        //@GET("api/Producto")
        //suspend fun getProductById(@Query("codigo") code:Int): Response<Product>

        //@GET("api/Producto/{codigo}")
        //suspend fun getProductById(@Path("codigo") code:Int): Response<Product>

        //@POST("api/Producto")
        //suspend fun saveProduct(@Body request: RegisterProductRequest) : Response<Result>
    }

    fun build():RemoteService{
        return builder.build().create(RemoteService::class.java)
    }

//    private fun getClient(): OkHttpClient {
//        return OkHttpClient().newBuilder().addInterceptor(HeaderInterceptor()).build()
//    }
}
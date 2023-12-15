package pe.edu.cibertec.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import pe.edu.cibertec.dto.AddPlateReq
import pe.edu.cibertec.dto.AddPlateRes
import pe.edu.cibertec.dto.LoginReq
import pe.edu.cibertec.dto.LoginRes
import pe.edu.cibertec.model.Plate
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.io.InputStream

object PlateRepository {
    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://unusual-tights-foal.cyclic.app/api/v0.1/plate/")
        .addConverterFactory(GsonConverterFactory.create())

    interface RemoteService{

        @POST("register")
        @Multipart
        suspend fun register(@Part("namePlate") namePlate: RequestBody,
                             @Part("descriptionPlate") descriptionPlate: RequestBody,
                             @Part("price") price: RequestBody,
                             @Part photo: MultipartBody.Part): Response<AddPlateRes>

        @GET("listAll")
        suspend fun getAll(): Response<List<Plate>>

        @GET("showPhoto/{nameFile}")
        suspend fun getPhoto(@Path("nameFile") nameFile: String): Response<InputStream>

        /*@Part("namePlate") namePlate: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("state") state: RequestBody*/

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
}
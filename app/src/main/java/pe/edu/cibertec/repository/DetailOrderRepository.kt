package pe.edu.cibertec.repository

import pe.edu.cibertec.dto.AddPlateRes
import pe.edu.cibertec.dto.OrderByStatePendingRes
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

object DetailOrderRepository {

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://unusual-tights-foal.cyclic.app/api/v0.1/detailOrder/")
        .addConverterFactory(GsonConverterFactory.create())


    interface RemoteService{

        @PUT("editStateDelivered/{idDetailOrder}/{nameState}")
        suspend fun editStateDelivered(@Path("idDetailOrder") idDetailOrder: Int, @Path("nameState") nameState: String): Response<AddPlateRes>


    }

    fun build():RemoteService{
        return builder.build().create(RemoteService::class.java)
    }

}
package pe.edu.cibertec.dto

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class AddPlateReq(
    /*@Part("namePlate")*/ val namePlate: RequestBody,
    /*@Part("description")*/ val descriptionPlate: RequestBody,
    /*@Part("price")*/ val price: RequestBody,
    val photo: MultipartBody.Part
)

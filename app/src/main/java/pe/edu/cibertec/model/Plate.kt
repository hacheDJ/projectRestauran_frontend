package pe.edu.cibertec.model

import com.google.gson.annotations.SerializedName

data class Plate(
    @SerializedName("id")
    val id: Int,
    @SerializedName("namePlate")
    val namePlate: String,
    @SerializedName("descriptionPlate")
    val descriptionPlate: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("state")
    val state: String
)

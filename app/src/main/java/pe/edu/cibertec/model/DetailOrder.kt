package pe.edu.cibertec.model

import com.google.gson.annotations.SerializedName

data class DetailOrder(
    @SerializedName("id")
    val id: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("state")
    val state: String,
    @SerializedName("idPlate")
    val idPlate: Plate,
    @SerializedName("idOrder")
    val idOrder: Order
)

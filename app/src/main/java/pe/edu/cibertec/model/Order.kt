package pe.edu.cibertec.model

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("id")
    val id: Int,
    @SerializedName("registrationDate")
    val registrationDate: String,
    @SerializedName("numberBoard")
    val numberBoard: Int,
    @SerializedName("state")
    val state: String,
    @SerializedName("idWaiter")
    val idWaiter: User
)

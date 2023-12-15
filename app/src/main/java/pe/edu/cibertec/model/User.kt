package pe.edu.cibertec.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("namesUser")
    val namesUser: String,
    @SerializedName("lastnameP")
    val lastnameP: String,
    @SerializedName("lastnameM")
    val lastnameM: String,
    @SerializedName("usernameLogin")
    val usernameLogin: String,
    @SerializedName("passwordLogin")
    val passwordLogin: String,
    @SerializedName("role")
    val role: String
)

package pe.edu.cibertec.dto

import pe.edu.cibertec.model.User

data class LoginRes(
    val err: Boolean,
    val msg: String,
    val data: User,
    val token: String
)

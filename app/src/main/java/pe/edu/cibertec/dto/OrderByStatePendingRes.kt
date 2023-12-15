package pe.edu.cibertec.dto

data class OrderByStatePendingRes(
    val id: Int,
    val quantity: Int,
    val state: String,
    val Order: OrderRes,
    val Plate: PlateRes
)

package pe.edu.cibertec.dto

data class ConfirmOrderReq(
    val order: OrderReq,
    val lstDetailOrder: List<DetailOrderReq>
)

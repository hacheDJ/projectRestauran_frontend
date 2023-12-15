package pe.edu.cibertec.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailOrderReq(
    val quantity: Int,
    val idPlate: Int
): Parcelable
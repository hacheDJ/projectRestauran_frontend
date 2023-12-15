package pe.edu.cibertec.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tb_preorder")
data class PreOrder(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val namePlate: String,
    var quantity: Int,
    val price: Double,
    val idPlate: Int
)

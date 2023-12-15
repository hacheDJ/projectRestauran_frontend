package pe.edu.cibertec.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import pe.edu.cibertec.model.PreOrder
import androidx.room.Dao

@Dao
interface PreOrderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(preOrder: PreOrder)

    @Delete
    fun delete(preOrder: PreOrder)

    @Query("SELECT * FROM tb_preorder ORDER BY id ASC")
    fun getAll(): List<PreOrder>

    @Update
    fun update(preOrder: PreOrder)

    @Query("DELETE FROM tb_preorder")
    suspend fun deleteAll()
//
//    @Query("SELECT * FROM tb_ShoppingCart ORDER BY id ASC")
//    fun getAllProducts(): List<ShoppingCart>
//
//    @Query("SELECT * FROM tb_ShoppingCart WHERE idProduct=:id")
//    fun findByIdProduct(id: Int): List<ShoppingCart>

}
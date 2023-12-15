package pe.edu.cibertec.util.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.cibertec.dao.PreOrderDAO
import pe.edu.cibertec.model.PreOrder

@Database(entities = [PreOrder::class], version = 1)
abstract class LocalDataBase: RoomDatabase() {
    abstract fun preorderDAO(): PreOrderDAO

    companion object{
        private var localDataBase: LocalDataBase? = null

        fun getInstance(context: Context) : LocalDataBase{
            if(localDataBase == null){
                localDataBase = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDataBase::class.java,
                    "db_local_restaurant"
                ).build()
            }

            return localDataBase!!
        }

    }
}
package pe.edu.cibertec.util.localstorage

import android.content.Context
import com.google.gson.Gson
import pe.edu.cibertec.model.User

class Prefs(val context: Context) {
    val STORAGE_NAME = "db"
    val JWTOKEN = "jwt"
    val SECRET_KEY = "secret"
    val ACTIVE_USER = "activeUser"
    val IS_AUTH = "isAuth"

    val storage = context.getSharedPreferences(STORAGE_NAME, 0)
    private val gson = Gson()

    public fun setJWT(jwtValue: String){
        storage.edit().putString(JWTOKEN, jwtValue).apply()
    }

    public fun getJWT(): String{
        return storage.getString(JWTOKEN, "")!!
    }

    public fun setActiveUser(user: User){
        val userJson = gson.toJson(user)
        storage.edit().putString(ACTIVE_USER, userJson).apply()
    }

    public fun getActiveUser(): User? {
        val userJson = storage.getString(ACTIVE_USER, "")!!
        val user: User? = if(userJson == "") null else gson.fromJson(userJson, User::class.java)

        return user
    }

    public fun setIsAuth(value: Boolean){
        storage.edit().putBoolean(IS_AUTH, value).apply()
    }

    public fun getIsAuth(): Boolean{
        return storage.getBoolean(IS_AUTH, false)!!
    }

    public fun clearStorage(){
        storage.edit().clear().apply()
    }
}
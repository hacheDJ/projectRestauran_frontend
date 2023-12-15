package pe.edu.cibertec.util.localstorage

import android.app.Application

class GlobalApp: Application() {
    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(applicationContext)
    }

    companion object{
        lateinit var prefs: Prefs
    }
}
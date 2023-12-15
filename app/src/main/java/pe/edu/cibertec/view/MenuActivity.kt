package pe.edu.cibertec.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import pe.edu.cibertec.R
import pe.edu.cibertec.databinding.ActivityMenuBinding
import pe.edu.cibertec.util.localstorage.GlobalApp
import pe.edu.cibertec.viewModel.MainViewModel
import pe.edu.cibertec.viewModel.MenuViewModel

class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    private val viewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.apply {
            viewModel.state.observe(this@MenuActivity){

                if(it.isAuth){
                    tvUsername.text = GlobalApp.prefs.getActiveUser()?.namesUser
                }else{
                    finish()
                }

                when(GlobalApp.prefs.getActiveUser()?.role){
                    "admin" -> {
                        btnListOrders.visibility = View.GONE
                        btnNewOrder.visibility = View.GONE
                    }
                    "waiter" -> {
                        btnAddPlate.visibility = View.GONE
                        btnNewOrder.visibility = View.GONE
                    }
                    "casher" -> {
                        btnAddPlate.visibility = View.GONE
                    }
                    "chef" -> {
                        btnAddPlate.visibility = View.GONE
                        btnNewOrder.visibility = View.GONE
                        btnListPlates.visibility = View.GONE
                    }
                    else -> println("Rol no válido")
                }

                btnAddPlate.setOnClickListener{
                    startActivity(Intent(this@MenuActivity, AddPlateActivity::class.java))
                }

                btnListPlates.setOnClickListener{
                    startActivity(Intent(this@MenuActivity, ListPlatesActivity::class.java))
                }
                btnListOrders.setOnClickListener {
                    startActivity(Intent(this@MenuActivity, ListOrdersActivity::class.java))
                }

                tvLinkLogOut.setOnClickListener{
                    GlobalApp.prefs.clearStorage()
                    finish()
                }

            }
        }


    }
}
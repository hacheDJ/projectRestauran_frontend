package pe.edu.cibertec.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.cibertec.R
import pe.edu.cibertec.adapter.PlateAdapter
import pe.edu.cibertec.databinding.ActivityListPlatesBinding
import pe.edu.cibertec.model.Plate
import pe.edu.cibertec.model.PreOrder
import pe.edu.cibertec.util.db.LocalDataBase
import pe.edu.cibertec.util.localstorage.GlobalApp
import pe.edu.cibertec.viewModel.ListPlatesViewModel

class ListPlatesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListPlatesBinding
    private val viewModel: ListPlatesViewModel by viewModels { ListPlatesViewModel.Factory(application) }

    private val plateAdapter = PlateAdapter(){plate ->
        println("OK!!!!!!!!!!!!!!!!")
        val preOrder = PreOrder(
            id = 0,
            namePlate = plate.namePlate,
            quantity = 1,
            price = plate.price,
            idPlate = plate.id
        )

        lifecycleScope.launch {
            val res = withContext(Dispatchers.IO){
              viewModel.addToPreOrder(preOrder)
            }

            Toast.makeText(applicationContext, if(res == null) "somethimes" else res, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListPlatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvLstPlates.adapter = plateAdapter

            fabShowPreOrders.setOnClickListener{
                startActivity(Intent(this@ListPlatesActivity, ListPreOrdersActivity::class.java))
            }

            viewModel.state.observe(this@ListPlatesActivity){
                pbListPlates.visibility = if(it.loading) View.VISIBLE else View.GONE
                fabShowPreOrders.isEnabled = !it.loading

                if(GlobalApp.prefs.getActiveUser()!!.role != "waiter") fabShowPreOrders.visibility = View.GONE

                plateAdapter.updateList(it.lstPlates)
            }
        }

    }


}
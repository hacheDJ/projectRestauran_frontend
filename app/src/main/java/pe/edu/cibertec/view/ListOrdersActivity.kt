package pe.edu.cibertec.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pe.edu.cibertec.R
import pe.edu.cibertec.adapter.OrderAdapter
import pe.edu.cibertec.databinding.ActivityListOrdersBinding
import pe.edu.cibertec.databinding.ActivityListPlatesBinding
import pe.edu.cibertec.util.localstorage.GlobalApp
import pe.edu.cibertec.viewModel.ListOrdersViewModel
import pe.edu.cibertec.viewModel.ListPlatesViewModel

class ListOrdersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListOrdersBinding
    private val viewModel: ListOrdersViewModel by viewModels()
    private val orderAdapter = OrderAdapter(){o, v ->
        println("VALUE_STATE"+v)

        lifecycleScope.launch {
            val msg = viewModel.updateState(o.id, v)
            Toast.makeText(this@ListOrdersActivity, msg, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvOrders.adapter = orderAdapter

            if(GlobalApp.prefs.getActiveUser()!!.role == "waiter")  tvTitleListOrders.text = "Lista de Pedidos Realizados"

            viewModel.state.observe(this@ListOrdersActivity){
                pbListOrdersPending.visibility = if(it.loading) View.VISIBLE else View.GONE

                orderAdapter.updateList(it.lstOrders)
                println("ORDERS_STATE---->"+it.lstOrders)
            }

        }

    }
}
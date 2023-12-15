package pe.edu.cibertec.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.cibertec.R
import pe.edu.cibertec.adapter.PreOrderAdapter
import pe.edu.cibertec.databinding.ActivityListPreOrdersBinding
import pe.edu.cibertec.dto.ConfirmOrderReq
import pe.edu.cibertec.dto.DetailOrderReq
import pe.edu.cibertec.dto.OrderReq
import pe.edu.cibertec.model.Order
import pe.edu.cibertec.util.db.LocalDataBase
import pe.edu.cibertec.util.localstorage.GlobalApp
import pe.edu.cibertec.viewModel.ListPreOrdersViewModel

class ListPreOrdersActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityListPreOrdersBinding
    private val viewModel: ListPreOrdersViewModel by viewModels { ListPreOrdersViewModel.Factory(application) }
    private val preOrderAdapter = PreOrderAdapter(
        onQuantityIncrement = { viewModel.incrementQuantity(it) },
        onQuantityDecrease = { viewModel.decreaseQuantity(it) }){
        viewModel.remove(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListPreOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewModel.showAll()



        binding.apply {
            rvPreOrders.adapter = preOrderAdapter



            viewModel.state.observe(this@ListPreOrdersActivity){
                pbListPreOrders.visibility = if(it.loading) View.VISIBLE else View.GONE

                var totalGeneral = 0.0

                it.lstPreOrders.forEach{po ->
                    totalGeneral += po.quantity * po.price
                }

                tvTotal.text = "Tolal: "+totalGeneral.toString()

                btnConfirmOrder.setOnClickListener {e ->
                    if(etNumberBoard == null || etNumberBoard.text.toString() == "") {
                        Toast.makeText(this@ListPreOrdersActivity, "Ingresa un número de mesa", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    if(it.lstPreOrders.isEmpty()){
                        Toast.makeText(this@ListPreOrdersActivity, "Añade platos a la preorden", Toast.LENGTH_LONG).show()
                    }

                    val order = OrderReq(
                        numberBoard = Integer.parseInt(etNumberBoard.text.toString()),
                        idWaiter = GlobalApp.prefs.getActiveUser()!!.id
                    )

                    val lstDetailOrder: MutableList<DetailOrderReq> = mutableListOf()

                    it.lstPreOrders.forEach {po ->
                        val dor = DetailOrderReq(
                            quantity = po.quantity,
                            idPlate = po.idPlate
                        )

                        lstDetailOrder.add(dor)
                    }

                    val confirmOrderReq = ConfirmOrderReq(order, lstDetailOrder)

                    lifecycleScope.launch {
                        val res = viewModel.confirmPreOrder(confirmOrderReq)
                        etNumberBoard.setText("")
                        Toast.makeText(this@ListPreOrdersActivity, res, Toast.LENGTH_LONG).show()
                    }

                }

                preOrderAdapter.updateList(it.lstPreOrders)





            }
        }

    }
}
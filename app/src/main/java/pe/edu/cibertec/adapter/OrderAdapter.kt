package pe.edu.cibertec.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import pe.edu.cibertec.R
import pe.edu.cibertec.databinding.ItemOrderBinding
import pe.edu.cibertec.databinding.ItemPreorderBinding
import pe.edu.cibertec.dto.OrderByStatePendingRes
import pe.edu.cibertec.model.Order
import pe.edu.cibertec.model.PreOrder

class OrderAdapter(var orders : List<OrderByStatePendingRes> = emptyList(), val onChangeState:(OrderByStatePendingRes, value: String) -> Unit) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding : ItemOrderBinding = ItemOrderBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(order: OrderByStatePendingRes) = with(binding){

            tvNamePlate.text = order.Plate.namePlate
            tvQuantity.text = order.quantity.toString()

            val lstStates = listOf<String>("pendiente", "entregado", "cancelado")
            spStates.setAdapter(ArrayAdapter(itemView.context, R.layout.item_states, lstStates))

            spStates.setText(order.Order.state, false)

            var value = ""

            spStates.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
                println("SPINNER-> "+adapterView+"view ->"+ view+"position-> "+position+"id-> "+id)

                when(position){
                    0 -> value = "pendiente"
                    1 -> value = "entregado"
                    2 -> value = "cancelado"
                }

            }

            btnChange.setOnClickListener {
                onChangeState(order, value)
            }



        }

    }

    fun updateList(orders:List<OrderByStatePendingRes>){
        this.orders = orders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }
}
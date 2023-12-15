package pe.edu.cibertec.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import pe.edu.cibertec.R
import pe.edu.cibertec.databinding.ItemPlateBinding
import pe.edu.cibertec.databinding.ItemPreorderBinding
import pe.edu.cibertec.model.Plate
import pe.edu.cibertec.model.PreOrder
import pe.edu.cibertec.util.localstorage.GlobalApp

class PreOrderAdapter(var preOrders : List<PreOrder> = emptyList(), val onQuantityIncrement:(PreOrder) -> Unit, val onQuantityDecrease:(PreOrder) -> Unit, val onItemDelete:(PreOrder) -> Unit) : RecyclerView.Adapter<PreOrderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding : ItemPreorderBinding = ItemPreorderBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(preOrder: PreOrder) = with(binding){
            //if(GlobalApp.prefs.getActiveUser()!!.role != "waiter") ivBtnAddToOrder.visibility = View.GONE else ivBtnAddToOrder.visibility = View.VISIBLE

            tvName.text = preOrder.namePlate
            etQuantity.setText(preOrder.quantity.toString())
            tvPrice.text = "S/. "+preOrder.price.toString()

            ivBtnIncrement.setOnClickListener {
                onQuantityIncrement(preOrder)
            }

            ivBtnDecrease.setOnClickListener {
                onQuantityDecrease(preOrder)
            }

            ivBtnDelete.setOnClickListener {
                onItemDelete(preOrder)
            }

        }

    }

    fun updateList(preOrders:List<PreOrder>){
        this.preOrders = preOrders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_preorder, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return preOrders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val preOrder = preOrders[position]
        holder.bind(preOrder)
    }
}
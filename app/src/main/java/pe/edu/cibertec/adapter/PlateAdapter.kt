package pe.edu.cibertec.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.cibertec.R
import pe.edu.cibertec.databinding.ItemPlateBinding
import pe.edu.cibertec.model.Plate
import com.squareup.picasso.Picasso
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import pe.edu.cibertec.util.localstorage.GlobalApp


class PlateAdapter( var plates : List<Plate> = emptyList(), val onItemAddToOrder:(Plate) -> Unit) : RecyclerView.Adapter<PlateAdapter.ViewHolder>() {

    private val storage = Firebase.storage
    val reference = storage.reference

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding : ItemPlateBinding = ItemPlateBinding.bind(itemView)

        fun bind(plate: Plate) = with(binding){
            if(GlobalApp.prefs.getActiveUser()!!.role != "waiter") ivBtnAddToOrder.visibility = View.GONE else ivBtnAddToOrder.visibility = View.VISIBLE

            tvName.text = plate.namePlate
            tvDescription.text = plate.descriptionPlate
            tvPrice.text = plate.price.toString()
            tvState.text = plate.state

            reference.child("upload/"+plate.photo)
                .downloadUrl
                .addOnSuccessListener {uri ->
                    Picasso.get().load(uri)
                        .resize(100,100)
                        .error(R.drawable.ic_launcher_background)
                        .into(ivPhoto)
                }.addOnFailureListener{
                    println("----> "+it.message)
                }

            ivBtnAddToOrder.setOnClickListener {
                onItemAddToOrder(plate)
            }

        }

    }

    fun updateList(plates:List<Plate>){
        this.plates = plates
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_plate, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return plates.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plate = plates[position]
        holder.bind(plate)
    }
}
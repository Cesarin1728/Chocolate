package com.example.adopta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EliminarAdapter (private val lista: MutableList<Mascota>) : RecyclerView.Adapter<EliminarAdapter.ViewHolderClass>(){

    private val itemSeleccion = mutableListOf<Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holder_eliminar, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolderClass,
        position: Int
    ) {
        val item = lista[position]
        holder.nombre.text = item.nombre
        holder.especie.text = item.especie
        holder.peso.text = item.peso

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = itemSeleccion.contains(position)

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!itemSeleccion.contains(position)) {
                    itemSeleccion.add(position)
                }
            } else {
                itemSeleccion.remove(position)
            }
        }
    }

    override fun getItemCount(): Int = lista.size

    fun eliminarCosa() {
        itemSeleccion.sortedDescending().forEach {
                index -> lista.removeAt(index)
        }

        itemSeleccion.clear()
        notifyDataSetChanged()
    }

    class ViewHolderClass (view : View) :
        RecyclerView.ViewHolder(view){
        val nombre = view.findViewById<TextView>(R.id.tvNombre)
        val especie = view.findViewById<TextView>(R.id.tvEspecie)
        val peso = view.findViewById<TextView>(R.id.tvPeso)
        val checkBox = view.findViewById<CheckBox>(R.id.cbSelect)
    }
}

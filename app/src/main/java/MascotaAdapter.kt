package com.example.adopta

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MascotaAdapter (private val lista: List<Mascota>) : RecyclerView.Adapter<MascotaAdapter.ViewHolderClass>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holder, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolderClass,
        position: Int
    ) {
        val item = lista[position]
        holder.nombre.text = item.nombre
        holder.raza.text = item.raza
        //Para el XML item_holder
        holder.nombre.setOnClickListener { //Revisar item_holder
            val context = holder.itemView.context
            val tarj = Intent(context, tarjeta::class.java)
            tarj.putExtra("pos", position)
            context.startActivity(tarj)
        }
    }

    override fun getItemCount(): Int = lista.size

    class ViewHolderClass (view : View) :
        RecyclerView.ViewHolder(view){
        val nombre = view.findViewById<TextView>(R.id.txtNombre)
        val raza = view.findViewById<TextView>(R.id.txtRaza)
    }

}

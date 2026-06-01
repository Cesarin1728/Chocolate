package com.example.chocolate

import android.view.LayoutInflater // Para convertir nuestro XML a un objeto view y utilizarlo
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Nuestro adaptador se llama EliminarAdapter.
// Recibe una lista (de chocolates) como parametro
// Hereda de RecyclerView.Adapter. Preguntar maestra
class EliminarAdapter (private val lista: MutableList<Chocolate>) : RecyclerView.Adapter<EliminarAdapter.ViewHolderClass>(){

    private val itemSeleccion = mutableListOf<Int>()

    // onCreateViewHolder es para crear un nuevo item y que el ViewHolder sepa donde inflarlo.
    // Osea que infla nuestro XML item_holder_eliminar para cada tarjeta y así poder utilizarlo
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holder_eliminar, parent, false)
        return ViewHolderClass(view) // Retorna un viewHolder del layout inflado
    }

    // Para vinvlular los datos que gestionamos con views que componen el Layout
    override fun onBindViewHolder(
        holder: ViewHolderClass,
        position: Int
    ) {
        val item = lista[position] // Chocolate de la posición del holder
        // Ponemos los datos del chocolate en el holder
        holder.nombre.text = item.nombre
        holder.tipo.text = item.tipo
        holder.peso.text = item.peso

        // Es como el listener de un botón, pero aquí verifica si a la checkbox se le hace click
        holder.checkBox.setOnCheckedChangeListener(null) // Quitamos temporalmente el listener. Para evitar que al reciclar un ViewHolder, el listener viejo se dispare po si solo al ser el mismo ViewHolder
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

    override fun getItemCount(): Int = lista.size // Para ver cuantos elementos se están gestionando, calcular los fuera de la zona visible y reutilizar los ViewHolder

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
        val tipo = view.findViewById<TextView>(R.id.tvTipo)
        val peso = view.findViewById<TextView>(R.id.tvPeso)
        val checkBox = view.findViewById<CheckBox>(R.id.cbSelect)
    }
}
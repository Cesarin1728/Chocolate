package com.example.adopta

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
// Nuestro adaptador se llama EliminarAdapter.
// Recibe una lista (de mascotas) como parametro
// Hereda de RecyclerView.Adapter. Preguntar maestra
// RecyclerView.Adapter<EliminarAdapter.ViewHolderClass> hereda y puede usar los métodos
class EliminarAdapter (private val lista: MutableList<Mascota>, private val context: Context) : RecyclerView.Adapter<EliminarAdapter.ViewHolderClass>(){

    private val itemSeleccion = mutableListOf<Int>()

    // onCreateViewHolder es para crear un nuevo item y que el ViewHolder sepa donde inflarlo.
    // Osea que infla nuestro XML item_holder_eliminar para cada tarjeta y así poder utilizarlo
    override fun onCreateViewHolder(
        parent: ViewGroup, //Para saber el tamaño y forma del contenedor
        viewType: Int // Se puede usar si tenemos diferentes diseños
    ): ViewHolderClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holder_eliminar, parent, false)
        return ViewHolderClass(view) // Retorna un viewHolder del layout inflado
    }

    // Para vinvlular los datos que gestionamos con views que componen el Layout
    override fun onBindViewHolder(
        holder: ViewHolderClass, //Nuestro contenedor del onCreate
        position: Int //Índice del elemento de la lsita
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

    override fun getItemCount(): Int = lista.size // Para ver cuantos elementos se están gestionando, calcular los fuera de la zona visible y reutilizar los ViewHolder

    fun eliminarCosa() {
        val indices = itemSeleccion.sortedDescending()
        var pendientes = indices.size

        if (pendientes == 0) {
            notifyDataSetChanged()
            return
        }

        for (index in indices) {
            val id = lista[index].id
            val peticion = object : StringRequest(
                Request.Method.POST,
                Config.URL_ELIMINAR,
                { respuesta ->
                    try {
                        val json = JSONObject(respuesta)
                        if (json.getBoolean("exito")) {
                            lista.removeAt(index)
                        } else {
                            Toast.makeText(context, json.getString("mensaje"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error al leer la respuesta", Toast.LENGTH_SHORT).show()
                    }
                    pendientes--
                    if (pendientes == 0) {
                        itemSeleccion.clear()
                        notifyDataSetChanged()
                    }
                },
                { error ->
                    Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                    pendientes--
                    if (pendientes == 0) {
                        itemSeleccion.clear()
                        notifyDataSetChanged()
                    }
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    return hashMapOf("id" to id.toString())
                }
            }

            VolleyCola.getInstance(context).requestQueue.add(peticion)
        }
    }

    class ViewHolderClass (view : View) :
        RecyclerView.ViewHolder(view){
        val nombre = view.findViewById<TextView>(R.id.tvNombre)
        val especie = view.findViewById<TextView>(R.id.tvEspecie)
        val peso = view.findViewById<TextView>(R.id.tvPeso)
        val checkBox = view.findViewById<CheckBox>(R.id.cbSelect)
    }
}
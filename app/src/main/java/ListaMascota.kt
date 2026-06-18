package com.example.adopta

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

object ListaMascota {
    val lista: MutableList<Mascota> = mutableListOf()

    fun cargarMascotas(context: Context) {
        val peticion = object : StringRequest(
            Request.Method.POST,
            Config.URL_LISTAR,
            { respuesta ->
                try {
                    val json = JSONObject(respuesta) // convertimos el texto de respuesta a JSON
                    if (json.getBoolean("exito")) { // leemos la variable "exito" que nos devuelve el php (true o false)
                        ListaMascota.lista.clear()
                        val arreglo = json.getJSONArray("mascotas")
                        for (i in 0 until arreglo.length()) {
                            val obj = arreglo.getJSONObject(i)
                            ListaMascota.lista.add(
                                Mascota(
                                    id = obj.getInt("id"),
                                    nombre = obj.getString("nombre"),
                                    raza = obj.getString("raza"),
                                    alimento = obj.getString("alimento"),
                                    telefonoContacto = obj.getString("telefono_contacto"),
                                    especie = obj.getString("especie"),
                                    edad = obj.getString("edad"),
                                    tamaño = obj.getString("tamanio"),
                                    pelaje = obj.getString("pelaje"),
                                    comportamiento = obj.getString("comportamiento"),
                                    peso = obj.getString("peso")
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Error al leer la respuesta", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        ) {}

        VolleyCola.getInstance(context).requestQueue.add(peticion) // le damos la petición a la cola de volley para ejecutarla
    }
}
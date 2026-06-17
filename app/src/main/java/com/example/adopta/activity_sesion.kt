package com.example.adopta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request // para los metodos HTPP (POST, GET, ETC)
import com.android.volley.toolbox.StringRequest // Para el tipo de petición (espera texto/JSON como respuesta)
import org.json.JSONObject // para convertir la respuesta JSON en PHP

class activity_sesion : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etContra: EditText
    private lateinit var btnIniciar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesion)

        etUsuario = findViewById(R.id.etUsuario)
        etContra = findViewById(R.id.etContra)
        btnIniciar = findViewById(R.id.btnIniciar)

        cargarMascotas()

        btnIniciar.setOnClickListener { iniciarSesion() }
    }

    private fun iniciarSesion() {
        val usuario = etUsuario.text.toString().trim()
        val clave = etContra.text.toString().trim()

        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val peticion = object : StringRequest( // creamos la petición de tipo StringRequest
            Request.Method.POST, // Se usa POST para envíar datos
            Config.URL_LOGIN, // le damos la URL de nuestro PHP
            { respuesta ->
                try {
                    val json = JSONObject(respuesta) // convertimos el texto de respuesta a JSON
                    if (json.getBoolean("exito")) { // leemos la variable "exito" que nos devuelve el php (true o false)
                        val rol = json.getString("rol") // leemos la variable "rol" que devuelve el JSON
                        val prefs = getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
                        prefs.edit()
                            .putString("rol", rol)
                            .apply()

                        if (rol == "Administrador") {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            startActivity(Intent(this, Ver::class.java))
                        }
                    } else {
                        val mensajeError = json.getString("mensaje")
                        Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al leer la respuesta", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        ) {
            // aquí definimos que datos vamos a mandar al php en el POST
            // volley ya tiene una función getParams, pero la vmsos a modificar para enviar nuestros datos
            override fun getParams(): MutableMap<String, String> { // el formato de los datos es con un mapa o diccionario
                return hashMapOf("nombre" to usuario, "clave" to clave) // para construir y devolver las parejas de datos
            }
        }

        VolleyCola.getInstance(this).requestQueue.add(peticion) // le damos la petición a la cola de volley para ejecutarla
    }

    private fun cargarMascotas() {
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
                    Toast.makeText(this, "Error al leer la respuesta", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        ) {}

        VolleyCola.getInstance(this).requestQueue.add(peticion) // le damos la petición a la cola de volley para ejecutarla
    }
}
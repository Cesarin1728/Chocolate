package com.example.adopta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class activity_sesion : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etContra: EditText
    private lateinit var btnIniciar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesion)

        etUsuario = findViewById(R.id.etUsuario)
        etContra  = findViewById(R.id.etContra)
        btnIniciar = findViewById(R.id.btnIniciar)

        btnIniciar.setOnClickListener { iniciarSesion() }
    }

    private fun iniciarSesion() {
        val usuario = etUsuario.text.toString().trim()
        val clave   = etContra.text.toString().trim()

        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val peticion = object : StringRequest(
            Request.Method.POST,
            Config.URL_LOGIN,
            { respuesta ->
                try {
                    val json = JSONObject(respuesta)
                    if (json.getBoolean("exito")) {
                        val rol = json.getString("rol")
                        val prefs = getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
                        prefs.edit()
                            .putString("rol", rol)
                            .putString("nombre", json.getString("nombre"))
                            .apply()

                        if (rol == "Administrador") {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            startActivity(Intent(this, Ver::class.java))
                        }
                    } else {
                        Toast.makeText(this, json.getString("mensaje"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al leer la respuesta", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error de conexión: ${error.message}", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf("nombre" to usuario, "clave" to clave)
            }
        }

        VolleySingleton.getInstance(this).requestQueue.add(peticion)
    }
}
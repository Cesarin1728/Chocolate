package com.example.chocolate

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_sesion : AppCompatActivity() {
    lateinit var etUsuario: EditText
    lateinit var etContra: EditText
    lateinit var btnIniciar: Button
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sesion)

        //sharedPref es una API para almacenar colecciones pequeñas de datos clave valor
        sharedPref = getSharedPreferences("Usuarios", Context.MODE_PRIVATE) //Abrimos o creamos el archivo Usuarios en modo privado [ara que solo nuestra app pueda acceder
        val editor = sharedPref.edit()
        editor.putString("admin_user", "Administrador")
        editor.putString("admin_pass", "admin1234")
        editor.putString("trabajador_user", "Trabajador")
        editor.putString("trabajador_pass", "traba1234")
        editor.apply()

        etUsuario = findViewById<EditText>(R.id.etUsuario)
        etContra = findViewById<EditText>(R.id.etContra)
        btnIniciar = findViewById<Button>(R.id.btnIniciar)

        btnIniciar.setOnClickListener { iniciar() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Registrar 10 chocolates
    // Modificar el país de un chocolate
    // Capturar los chocolates en aditar (incluyendo antes y después del cambio de país)
    // Eliminar el 5to y penúltimo chocolate
    // Tomar captura de los chocolates que quedaron
    // Capturas error sesión (para los 3 casos)

    private fun iniciar() {
        val user = etUsuario.text.toString()
        val pass = etContra.text.toString()

        val adminUser = sharedPref.getString("admin_user", "Administrador")
        val adminPass = sharedPref.getString("admin_pass", "admin1234")
        val trabUser = sharedPref.getString("trabajador_user", "Trabajador")
        val trabPass = sharedPref.getString("trabajador_pass", "traba1234")

        if (user == adminUser && pass == adminPass) {
            sharedPref.edit().putString("rol", "Administrador").apply()
            startActivity(Intent(this, MainActivity::class.java))
        } else if (user == trabUser && pass == trabPass) {
            sharedPref.edit().putString("rol", "Trabajador").apply()
            if (ListaChocolate.lista.isEmpty()) {
                Toast.makeText(this, "No hay chocolates :(", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this, Ver::class.java))
        } else if (user == adminUser || user == trabUser) {
            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show()
        }
    }
}

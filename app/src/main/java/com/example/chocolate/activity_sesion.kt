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

        sharedPref = getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
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
            startActivity(Intent(this, Ver::class.java))
        } else if (user == adminUser || user == trabUser) {
            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show()
        }
    }
}

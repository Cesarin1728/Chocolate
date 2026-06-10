package com.example.adopta

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class tarjeta : AppCompatActivity() {
    private val REQUEST_CALL = 1
    lateinit var tvNombre: TextView
    lateinit var tvRaza: TextView
    lateinit var tvAlimento: TextView
    lateinit var tvTelefono: TextView
    lateinit var tvEspecie: TextView
    lateinit var tvEdad: TextView
    lateinit var tvTamaño: TextView
    lateinit var tvPelaje: TextView
    lateinit var tvComportamiento: TextView
    lateinit var tvPeso: TextView
    lateinit var btnllamar : Button
    lateinit var btnVolver : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tarjeta)

        tvNombre = findViewById(R.id.tvNombre)
        tvRaza = findViewById(R.id.tvRaza)
        tvAlimento = findViewById(R.id.tvAlimento)
        tvTelefono = findViewById(R.id.tvTelefono)
        tvEspecie = findViewById(R.id.tvEspecie)
        tvEdad = findViewById(R.id.tvEdad)
        tvTamaño = findViewById(R.id.tvTamaño)
        tvPelaje = findViewById(R.id.tvPelaje)
        tvComportamiento = findViewById(R.id.tvComportamiento)
        tvPeso = findViewById(R.id.tvPeso)
        btnllamar = findViewById(R.id.btnllamar)
        btnVolver = findViewById(R.id.btnVolver)
        btnllamar.setOnClickListener { llamar() }
        btnVolver.setOnClickListener { volver() }

        val posicion: Int
        posicion = intent.getIntExtra("pos", -1)
        val item = ListaMascota.lista[posicion]
        tvNombre.text = item.nombre
        tvRaza.text = item.raza
        tvAlimento.text = item.alimento
        tvTelefono.text = item.telefonoContacto
        tvEspecie.text = item.especie
        tvEdad.text = item.edad
        tvTamaño.text = item.tamaño
        tvPelaje.text = item.pelaje
        tvComportamiento.text = item.comportamiento
        tvPeso.text = item.peso

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun llamar() {
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL
            )
        }
        else {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + tvTelefono.text.toString())
            startActivity(intent)
        }
    }

    private fun volver(){
        val cambio = Intent(this, Ver::class.java)
        startActivity(cambio)
    }

}

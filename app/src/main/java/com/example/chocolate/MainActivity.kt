package com.example.chocolate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    lateinit var etNombre: EditText
    lateinit var etRaza: EditText
    lateinit var etAlimento: EditText
    lateinit var etTelefono: EditText
    lateinit var spEspecie: Spinner
    lateinit var etEdad: EditText
    lateinit var spTamaño: Spinner
    lateinit var spPelaje: Spinner
    lateinit var spComportamiento: Spinner
    lateinit var spPeso: Spinner
    lateinit var btnAceptar : Button
    lateinit var btnVer : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar :Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        etNombre = findViewById(R.id.etNombre)
        etRaza = findViewById(R.id.etRaza)
        etAlimento = findViewById(R.id.etAlimento)
        etTelefono = findViewById(R.id.etTelefono)
        spEspecie = findViewById(R.id.spEspecie)
        etEdad = findViewById(R.id.etEdad)
        spTamaño = findViewById(R.id.spTamaño)
        spPelaje = findViewById(R.id.spPelaje)
        spComportamiento = findViewById(R.id.spComportamiento)
        spPeso = findViewById(R.id.spPeso)
        btnAceptar = findViewById(R.id.btnAceptar)
        btnVer = findViewById(R.id.btnVer)

        val especie = resources.getStringArray(R.array.especie)
        val tamaño = resources.getStringArray(R.array.tamaño)
        val pelaje = resources.getStringArray(R.array.pelaje)
        val comportamiento = resources.getStringArray(R.array.comportamiento)
        val peso = resources.getStringArray(R.array.peso)

        val adapterEspecie = ArrayAdapter(this, android.R.layout.simple_spinner_item, especie)
        adapterEspecie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spEspecie.adapter = adapterEspecie

        val adapterTamaño = ArrayAdapter(this, android.R.layout.simple_spinner_item, tamaño)
        adapterTamaño.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTamaño.adapter = adapterTamaño

        val adapterPelaje = ArrayAdapter(this, android.R.layout.simple_spinner_item, pelaje)
        adapterPelaje.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPelaje.adapter = adapterPelaje

        val adapterComportamiento = ArrayAdapter(this, android.R.layout.simple_spinner_item, comportamiento)
        adapterComportamiento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spComportamiento.adapter = adapterComportamiento

        val adapterPeso = ArrayAdapter(this, android.R.layout.simple_spinner_item, peso)
        adapterPeso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPeso.adapter = adapterPeso

        btnAceptar.setOnClickListener { guardar() }
        btnVer.setOnClickListener { mostrar() }
    }

    private fun mostrar (){
        var mostrar = Intent(this, Ver::class.java)
        startActivity(mostrar)
    }

    private fun guardar() {
        val nombre = etNombre.text.toString()
        val raza = etRaza.text.toString()
        val alimento = etAlimento.text.toString()
        val telefono = etTelefono.text.toString()
        val edad = etEdad.text.toString()

        if (nombre.isEmpty() || raza.isEmpty() || alimento.isEmpty() || telefono.isEmpty() || edad.isEmpty()) {
            Toast.makeText(this, "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show()
            return
        }

        if (telefono.length != 10 ){
            Toast.makeText(this, "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
            return
        }

        val edadValor = edad.toDoubleOrNull()
        if (edadValor == null || edadValor < 0 || edadValor > 100) {
            Toast.makeText(this, "La edad debe estar entre 0 y 100", Toast.LENGTH_SHORT).show()
            return
        }

        val info = Chocolate(
            nombre = nombre,
            raza = raza,
            alimento = alimento,
            telefonoContacto = telefono,
            especie = spEspecie.selectedItem.toString(),
            edad = edad,
            tamaño = spTamaño.selectedItem.toString(),
            pelaje = spPelaje.selectedItem.toString(),
            comportamiento = spComportamiento.selectedItem.toString(),
            peso = spPeso.selectedItem.toString()
        )

        ListaChocolate.lista.add(info)
        Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.getItemId() == R.id.opc1){
//            val cambio = Intent(this, MainActivity::class.java)
//            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc2){
            val cambio = Intent(this, Ver::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc3){
            val cambio = Intent(this, guardarcambios::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc4){
            val cambio = Intent(this, eliminarchocolate::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc5){
            val cambio = Intent(this, activity_sesion::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc6){
            val cambio = Intent(this, contacto::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc7){
            val cambio = Intent(this, creador::class.java)
            startActivity(cambio)
        }

        return super.onOptionsItemSelected(item)
    }
}
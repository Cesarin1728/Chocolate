package com.example.adopta

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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class guardarcambios : AppCompatActivity() {

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
    lateinit var btnAnterior: Button
    lateinit var btnGuardar: Button
    lateinit var btnSiguiente: Button

    lateinit var especie: Array<String>
    lateinit var tamaño: Array<String>
    lateinit var pelaje: Array<String>
    lateinit var comportamiento: Array<String>
    lateinit var peso: Array<String>

    private var mascotaActual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guardarcambios)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
        btnAnterior = findViewById(R.id.btnAnterior)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnSiguiente = findViewById(R.id.btnSiguiente)

        especie = resources.getStringArray(R.array.especie)
        tamaño = resources.getStringArray(R.array.tamaño)
        pelaje = resources.getStringArray(R.array.pelaje)
        comportamiento = resources.getStringArray(R.array.comportamiento)
        peso = resources.getStringArray(R.array.peso)

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

        if (ListaMascota.lista.isNotEmpty()) {
            mostrarMascota(mascotaActual)
        }

        btnSiguiente.setOnClickListener { siguiente() }
        btnAnterior.setOnClickListener { anterior() }
        btnGuardar.setOnClickListener { guardarCambios() }
    }

    private fun siguiente() {
        if (ListaMascota.lista.isNotEmpty()) {
            mascotaActual = mascotaActual + 1
            if (mascotaActual >= ListaMascota.lista.size) {
                mascotaActual = 0
            }
            mostrarMascota(mascotaActual)
        }
    }

    private fun anterior() {
        if (ListaMascota.lista.isNotEmpty()) {
            mascotaActual = mascotaActual - 1
            if (mascotaActual < 0) {
                mascotaActual = ListaMascota.lista.size - 1
            }
            mostrarMascota(mascotaActual)
        }
    }

    private fun mostrarMascota(pos: Int) {
        val mascota = ListaMascota.lista[pos]
        etNombre.setText(mascota.nombre)
        etRaza.setText(mascota.raza)
        etAlimento.setText(mascota.alimento)
        etTelefono.setText(mascota.telefonoContacto)
        etEdad.setText(mascota.edad)

        seleccionSpinner(spEspecie, especie, mascota.especie)
        seleccionSpinner(spTamaño, tamaño, mascota.tamaño)
        seleccionSpinner(spPelaje, pelaje, mascota.pelaje)
        seleccionSpinner(spComportamiento, comportamiento, mascota.comportamiento)
        seleccionSpinner(spPeso, peso, mascota.peso)
    }

    private fun seleccionSpinner(spinner: Spinner, array: Array<String>, valor: String) {
        val posicion = array.indexOf(valor)
        spinner.setSelection(posicion)
    }

    private fun guardarCambios() {
        if (ListaMascota.lista.isEmpty()) {
            Toast.makeText(this, "No hay mascotas registradas", Toast.LENGTH_SHORT).show()
            return
        }

        val nombre = etNombre.text.toString()
        val raza = etRaza.text.toString()
        val alimento = etAlimento.text.toString()
        val telefono = etTelefono.text.toString()
        val edad = etEdad.text.toString()

        if (nombre.isEmpty() || raza.isEmpty() || alimento.isEmpty() || telefono.isEmpty() || edad.isEmpty()) {
            Toast.makeText(this, "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show()
            return
        }

        if (telefono.length != 10) {
            Toast.makeText(this, "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
            return
        }

        val edadValor = edad.toDoubleOrNull()
        if (edadValor == null || edadValor < 0 || edadValor > 100) {
            Toast.makeText(this, "La edad debe estar entre 0 y 100", Toast.LENGTH_SHORT).show()
            return
        }

        val id = ListaMascota.lista[mascotaActual].id // obtenemos el id de la mascota actual

        val peticion = object : StringRequest(
            Request.Method.POST,
            Config.URL_EDITAR,
            { respuesta ->
                try {
                    val json = JSONObject(respuesta)
                    if (json.getBoolean("exito")) {
                        ListaMascota.lista[mascotaActual] = Mascota(
                            id             = id,
                            nombre         = nombre,
                            raza           = raza,
                            alimento       = alimento,
                            telefonoContacto = telefono,
                            especie        = spEspecie.selectedItem.toString(),
                            edad           = edad,
                            tamaño         = spTamaño.selectedItem.toString(),
                            pelaje         = spPelaje.selectedItem.toString(),
                            comportamiento = spComportamiento.selectedItem.toString(),
                            peso           = spPeso.selectedItem.toString()
                        )
                        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
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
                return hashMapOf(
                    "id"             to id.toString(),
                    "nombre"         to nombre,
                    "raza"           to raza,
                    "alimento"       to alimento,
                    "telefono"       to telefono,
                    "especie"        to spEspecie.selectedItem.toString(),
                    "edad"           to edad,
                    "tamanio"        to spTamaño.selectedItem.toString(),
                    "pelaje"         to spPelaje.selectedItem.toString(),
                    "comportamiento" to spComportamiento.selectedItem.toString(),
                    "peso"           to spPeso.selectedItem.toString()
                )
            }
        }

        VolleySingleton.getInstance(this).requestQueue.add(peticion)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.getItemId() == R.id.opc1){
            val cambio = Intent(this, MainActivity::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc2){
            val cambio = Intent(this, Ver::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc3){
//            val cambio = Intent(this, guardarcambios::class.java)
//            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc4){
            val cambio = Intent(this, eliminarmascota::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc5){
            val prefs = getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
            prefs.edit().remove("rol").apply()
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
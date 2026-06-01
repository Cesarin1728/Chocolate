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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class guardarcambios : AppCompatActivity() {

    lateinit var etNombre: EditText
    lateinit var etMarca: EditText
    lateinit var etPaisOrigen: EditText
    lateinit var etTelefono: EditText
    lateinit var spPresentacion: Spinner
    lateinit var etPorcentajeCacao: EditText
    lateinit var spTipoCacao: Spinner
    lateinit var spPerfilSabor: Spinner
    lateinit var spTipo: Spinner
    lateinit var spPeso: Spinner
    lateinit var btnAnterior: Button
    lateinit var btnGuardar: Button
    lateinit var btnSiguiente: Button

    lateinit var presentacion: Array<String>
    lateinit var tipoCacao: Array<String>
    lateinit var perfilSabor: Array<String>
    lateinit var tipo: Array<String>
    lateinit var peso: Array<String>

    private var chocolateActual = 0

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
        etMarca = findViewById(R.id.etMarca)
        etPaisOrigen = findViewById(R.id.etPaisOrigen)
        etTelefono = findViewById(R.id.etTelefono)
        spPresentacion = findViewById(R.id.spPresentacion)
        etPorcentajeCacao = findViewById(R.id.etPorcentajeCacao)
        spTipoCacao = findViewById(R.id.spTipoCacao)
        spPerfilSabor = findViewById(R.id.spPerfilSabor)
        spTipo = findViewById(R.id.spTipo)
        spPeso = findViewById(R.id.spPeso)
        btnAnterior = findViewById(R.id.btnAnterior)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnSiguiente = findViewById(R.id.btnSiguiente)

        presentacion = resources.getStringArray(R.array.presentacion)
        tipoCacao = resources.getStringArray(R.array.tipoCacao)
        perfilSabor = resources.getStringArray(R.array.perfilSabor)
        tipo = resources.getStringArray(R.array.tipo)
        peso = resources.getStringArray(R.array.peso)

        val adapterPresentacion = ArrayAdapter(this, android.R.layout.simple_spinner_item, presentacion)
        adapterPresentacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPresentacion.adapter = adapterPresentacion

        val adapterTipoCacao = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoCacao)
        adapterTipoCacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipoCacao.adapter = adapterTipoCacao

        val adapterPerfilSabor = ArrayAdapter(this, android.R.layout.simple_spinner_item, perfilSabor)
        adapterPerfilSabor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPerfilSabor.adapter = adapterPerfilSabor

        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipo)
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipo.adapter = adapterTipo

        val adapterPeso = ArrayAdapter(this, android.R.layout.simple_spinner_item, peso)
        adapterPeso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPeso.adapter = adapterPeso

        // Mostrar el primer chocolate, también se pudo hacer con el .size y ver si es mayor a cero
        if (ListaChocolate.lista.isNotEmpty()) {
            mostrarChocolate(chocolateActual)
        } else {
            Toast.makeText(this, "No hay chocolates todavíá", Toast.LENGTH_SHORT).show()
        }

        btnSiguiente.setOnClickListener { siguiente() }
        btnAnterior.setOnClickListener { anterior() }
        btnGuardar.setOnClickListener { guardarCambios() }
    }

    private fun siguiente() {
        if (ListaChocolate.lista.isNotEmpty()) {
            chocolateActual = (chocolateActual + 1) % ListaChocolate.lista.size
            mostrarChocolate(chocolateActual)
        }
    }

    private fun anterior() {
        if (ListaChocolate.lista.isNotEmpty()) {
            chocolateActual = if (chocolateActual - 1 < 0) ListaChocolate.lista.size - 1
            else chocolateActual - 1
            mostrarChocolate(chocolateActual)
        }
    }

    private fun mostrarChocolate(pos: Int) {
        val chocolate = ListaChocolate.lista[pos]
        etNombre.setText(chocolate.nombre)
        etMarca.setText(chocolate.marca)
        etPaisOrigen.setText(chocolate.paisOrigen)
        etTelefono.setText(chocolate.telefonoContacto)
        etPorcentajeCacao.setText(chocolate.porcentajeCacao)

        seleccionSpinner(spPresentacion, presentacion, chocolate.presentacion)
        seleccionSpinner(spTipoCacao, tipoCacao, chocolate.tipoCacao)
        seleccionSpinner(spPerfilSabor, perfilSabor, chocolate.perfilSabor)
        seleccionSpinner(spTipo, tipo, chocolate.tipo)
        seleccionSpinner(spPeso, peso, chocolate.peso)
    }

    private fun seleccionSpinner(spinner: Spinner, array: Array<String>, valor: String) {
        val posicion = array.indexOf(valor)
        if (posicion >= 0) {
            spinner.setSelection(posicion)
        }
    }

    private fun guardarCambios() {
        if (ListaChocolate.lista.isEmpty()) {
            Toast.makeText(this, "No hay chocolates", Toast.LENGTH_SHORT).show()
            return
        }

        val nombre = etNombre.text.toString()
        val marca = etMarca.text.toString()
        val pais = etPaisOrigen.text.toString()
        val telefono = etTelefono.text.toString()
        val porcentajecacao = etPorcentajeCacao.text.toString()

        if (nombre.isEmpty() || marca.isEmpty() || pais.isEmpty() || telefono.isEmpty() || porcentajecacao.isEmpty()) {
            Toast.makeText(this, "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show()
            return
        }

        if (telefono.length != 10) {
            Toast.makeText(this, "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
            return
        }

        val porcentaje = porcentajecacao.toDoubleOrNull()
        if (porcentaje == null || porcentaje < 0 || porcentaje > 100) {
            Toast.makeText(this, "El porcentaje de cacao debe estar entre 0 y 100", Toast.LENGTH_SHORT).show()
            return
        }

        val chocolateNuevo = Chocolate(
            nombre = nombre,
            marca = marca,
            paisOrigen = pais,
            telefonoContacto = telefono,
            presentacion = spPresentacion.selectedItem.toString(),
            porcentajeCacao = porcentajecacao,
            tipoCacao = spTipoCacao.selectedItem.toString(),
            perfilSabor = spPerfilSabor.selectedItem.toString(),
            tipo = spTipo.selectedItem.toString(),
            peso = spPeso.selectedItem.toString()
        )

        ListaChocolate.lista[chocolateActual] = chocolateNuevo
        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.getItemId() == R.id.opc1){ //Principal - Registro
            val cambio = Intent(this, MainActivity::class.java)
            startActivity(cambio)
//            Toast.makeText(this, "Ya estás en la opción", Toast.LENGTH_LONG).show()
        }
        if(item.getItemId() == R.id.opc2){ //ver
            val cambio = Intent(this, Ver::class.java)
            startActivity(cambio)
//            Toast.makeText(this, "Ya estás en la opción", Toast.LENGTH_LONG).show()
        }
        if(item.getItemId() == R.id.opc3){ //guardarcambios
//            val cambio = Intent(this, guardarcambios::class.java)
//            startActivity(cambio)
            Toast.makeText(this, "Ya estás en la opción", Toast.LENGTH_LONG).show()
        }
        if(item.getItemId() == R.id.opc4){ //elimianr
            val cambio = Intent(this, eliminarchocolate::class.java)
            startActivity(cambio)
//            Toast.makeText(this, "Ya estás en la opción", Toast.LENGTH_LONG).show()
        }
        if(item.getItemId() == R.id.opc5){ //cerrar sesion
            val cambio = Intent(this, activity_sesion::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc6){ //Contacto
            val cambio = Intent(this, contacto::class.java)
            startActivity(cambio)
//            Toast.makeText(this, "Ya estás en la opción", Toast.LENGTH_LONG).show()
        }
        if(item.getItemId() == R.id.opc7){ //Creador
            val cambio = Intent(this, creador::class.java)
            startActivity(cambio)
//            Toast.makeText(this, "Ya estás en la opción", Toast.LENGTH_LONG).show()
        }

        return super.onOptionsItemSelected(item)
    }
}
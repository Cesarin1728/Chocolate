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
    lateinit var etMarca: EditText
    lateinit var etPaisOrigen: EditText
    lateinit var etTelefono: EditText
    lateinit var spPresentacion: Spinner
    lateinit var etPorcentajeCacao: EditText
    lateinit var spTipoCacao: Spinner
    lateinit var spPerfilSabor: Spinner
    lateinit var spTipo: Spinner
    lateinit var spPeso: Spinner
    lateinit var btnAceptar : Button
    lateinit var btnVer : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar :Toolbar = findViewById<Toolbar>(R.id.toolbar) //val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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
        btnAceptar = findViewById(R.id.btnAceptar)
        btnVer = findViewById(R.id.btnVer)

        val presentacion = resources.getStringArray(R.array.presentacion)
        val tipoCacao = resources.getStringArray(R.array.tipoCacao)
        val perfilSabor = resources.getStringArray(R.array.perfilSabor)
        val tipo = resources.getStringArray(R.array.tipo)
        val peso = resources.getStringArray(R.array.peso)

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

        btnAceptar.setOnClickListener { guardar() }
        btnVer.setOnClickListener { mostrar() }
    }

    private fun mostrar (){
        var mostrar = Intent(this, Ver::class.java)
        startActivity(mostrar)
    }

    private fun guardar() {
        val nombre = etNombre.text.toString()
        val marca = etMarca.text.toString()
        val pais = etPaisOrigen.text.toString()
        val telefono = etTelefono.text.toString()
        val porcentajecacao = etPorcentajeCacao.text.toString()

        if (nombre.isEmpty() || marca.isEmpty() || pais.isEmpty() || telefono.isEmpty() || porcentajecacao.isEmpty()) {
            Toast.makeText(this, "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show()
            return
        }

        if (telefono.length != 10 ){
            Toast.makeText(this, "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
            return
        }

        val porcentaje = porcentajecacao.toDoubleOrNull()
        if (porcentaje == null || porcentaje < 0 || porcentaje > 100) {
            Toast.makeText(this, "El porcentaje de cacao debe estar entre 0 y 100", Toast.LENGTH_SHORT).show()
            return
        }

        val info = Chocolate(
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

        ListaChocolate.lista.add(info)
        Toast.makeText(this, "Chocolate guardado", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu) //(R.Carpeta.Archivo.tipomenu)
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

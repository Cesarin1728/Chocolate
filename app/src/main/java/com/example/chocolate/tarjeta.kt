package com.example.chocolate

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class tarjeta : AppCompatActivity() {
    private val REQUEST_CALL = 1 //Número que queramos
    lateinit var tvNombre: TextView
    lateinit var tvMarca: TextView
    lateinit var tvPaisOrigen: TextView
    lateinit var tvTelefono: TextView
    lateinit var tvPresentacion: TextView
    lateinit var tvPorcentajeCacao: TextView
    lateinit var tvTipoCacao: TextView
    lateinit var tvPerfilSabor: TextView
    lateinit var tvTipo: TextView
    lateinit var tvPeso: TextView
    lateinit var btnllamar : Button
    lateinit var btnVolver : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tarjeta)

        tvNombre = findViewById(R.id.tvNombre)
        tvMarca = findViewById(R.id.tvMarca)
        tvPaisOrigen = findViewById(R.id.tvPaisOrigen)
        tvTelefono = findViewById(R.id.tvTelefono)
        tvPresentacion = findViewById(R.id.tvPresentacion)
        tvPorcentajeCacao = findViewById(R.id.tvPorcentajeCacao)
        tvTipoCacao = findViewById(R.id.tvTipoCacao)
        tvPerfilSabor = findViewById(R.id.tvPerfilSabor)
        tvTipo = findViewById(R.id.tvTipo)
        tvPeso = findViewById(R.id.tvPeso)
        btnllamar = findViewById(R.id.btnllamar)
        btnVolver = findViewById(R.id.btnVolver)
        btnllamar.setOnClickListener { llamar() }
        btnVolver.setOnClickListener { volver() }

        val posicion: Int
        posicion=intent.getIntExtra("pos", -1) //Variable de tipo posición
        val item = ListaChocolate.lista [posicion]//Igualamos a la mutable list de ListaChocolate
        tvNombre.text = item.nombre
        tvMarca.text = item.marca
        tvPaisOrigen.text = item.paisOrigen
        tvTelefono.text = item.telefonoContacto
        tvPresentacion.text = item.presentacion
        tvPorcentajeCacao.text = item.porcentajeCacao
        tvTipoCacao.text = item.tipoCacao
        tvPerfilSabor.text = item.perfilSabor
        tvTipo.text = item.tipo
        tvPeso.text = item.peso
        
        
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun llamar() {
        //Necesitamos que el usuario nos de autorización
        //Recibe contexto y permiso que se necesita
        if(ContextCompat.checkSelfPermission(
            this,
                Manifest.permission.CALL_PHONE
        )!= PackageManager.PERMISSION_GRANTED //Garantizar permiso en caso de no estar autorizado
            ) {
            ActivityCompat.requestPermissions(//Si no está, esta parte nos manda el recuadro para autorizar
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL
            )
        }
        else{ //En este caso usaremos el Intent para dar un servicio
            val intent = Intent(Intent.ACTION_CALL) //En este caso queremos una acción de llamar, entonces le damos diferentes parametros
            //Parecido a una URL, en este caso identifica algo de forma general
            //En este caso para identificar un recurso
            intent.data = Uri.parse("tel:" + tvTelefono.text.toString()) //Obtenemos el número del tvTelefono
            startActivity(intent)

        }
    }

    private fun volver(){
        val cambio = Intent(this, Ver::class.java)
        startActivity(cambio)
    }

}
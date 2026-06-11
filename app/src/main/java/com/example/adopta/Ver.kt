package com.example.adopta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class Ver : AppCompatActivity() {
    lateinit var recy: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver)

        val toolbar : Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        recy = findViewById<RecyclerView>(R.id.rv)
        recy.layoutManager = LinearLayoutManager(this) //Como se organizan los elemetnos

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listar()
    }

    private fun listar() {
        val peticion = object : StringRequest(
            Request.Method.POST,
            Config.URL_LISTAR,
            { respuesta ->
                try {
                    val json = JSONObject(respuesta)
                    if (json.getBoolean("exito")) {
                        ListaMascota.lista.clear()
                        val arreglo = json.getJSONArray("mascotas")
                        for (i in 0 until arreglo.length()) {
                            val obj = arreglo.getJSONObject(i)
                            ListaMascota.lista.add(
                                Mascota(
                                    id             = obj.getInt("id"),
                                    nombre         = obj.getString("nombre"),
                                    raza           = obj.getString("raza"),
                                    alimento       = obj.getString("alimento"),
                                    telefonoContacto = obj.getString("telefono_contacto"),
                                    especie        = obj.getString("especie"),
                                    edad           = obj.getString("edad"),
                                    tamaño         = obj.getString("tamanio"),
                                    pelaje         = obj.getString("pelaje"),
                                    comportamiento = obj.getString("comportamiento"),
                                    peso           = obj.getString("peso")
                                )
                            )
                        }
                        val adapter = MascotaAdapter(ListaMascota.lista) //Asignamos le adapter y le pasamos la lista de chocolates
                        recy.adapter = adapter //Le damos a nuestra vista reciclada el adaptador
                        adapter.notifyDataSetChanged() //Para que haga las actualizaciones al adaptador, como si le dijera "Hey te actualizaste"
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
        ) {}

        VolleySingleton.getInstance(this).requestQueue.add(peticion)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val sharedPref = getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
        val rol = sharedPref.getString("rol", "")
        if (rol == "Trabajador") {
            menu?.findItem(R.id.opc1)?.isVisible = false //? solo si el objeto no es null no hace, si es null no hace nada
            menu?.findItem(R.id.opc2)?.isVisible = false
            menu?.findItem(R.id.opc3)?.isVisible = false
            menu?.findItem(R.id.opc4)?.isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.getItemId() == R.id.opc1){
            val cambio = Intent(this, MainActivity::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc2){
//            val cambio = Intent(this, Ver::class.java)
//            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc3){
            val cambio = Intent(this, guardarcambios::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc4){
            val cambio = Intent(this, eliminarmascota::class.java)
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
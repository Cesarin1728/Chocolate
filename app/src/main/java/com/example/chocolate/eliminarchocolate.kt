package com.example.chocolate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class eliminarchocolate : AppCompatActivity() {
    
    lateinit var recy: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eliminarchocolate)

        val toolbar : Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        recy=findViewById<RecyclerView>(R.id.rv)
        recy.layoutManager= LinearLayoutManager(this)

        val adapter = EliminarAdapter(ListaChocolate.lista)
        recy.adapter=adapter
        adapter.notifyDataSetChanged() //Para que haga las actualizaciones al adaptador, como si le dijera "Hey te actualizaste"

        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        btnEliminar.setOnClickListener {
            adapter.eliminarCosa()
            if (ListaChocolate.lista.isEmpty()) {
                Toast.makeText(this, "No hay chocolates :(", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Chocolates eliminados", Toast.LENGTH_SHORT).show()
            }
        }
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
            val cambio = Intent(this, guardarcambios::class.java)
            startActivity(cambio)
        }
        if(item.getItemId() == R.id.opc4){
//            val cambio = Intent(this, eliminarchocolate::class.java)
//            startActivity(cambio)
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
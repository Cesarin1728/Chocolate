package com.example.adopta

import android.content.Context // lo usa volley para acceder a la caché del dispositivo y sistema de red
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyCola private constructor(context: Context) {
    val requestQueue: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    companion object { //companion en como static, se puede llamar sin instanciar la clase
        @Volatile // para que le valor de "instancia" siempre sea de la memoria principal y no de caché. Para tener una sola cola de peticiones
        private var instancia: VolleyCola? = null

        fun getInstance(context: Context): VolleyCola {
            return instancia ?: synchronized(this) { // ?: si la instancia no es null la devuelve simplemente, si es null con synchronized se cre una nueva
                instancia ?: VolleyCola(context).also { instancia = it } // si sigue siendo null crea una nueva instancia, luego guarda la nueva instancia para usarla después
            }
        }
    }
}
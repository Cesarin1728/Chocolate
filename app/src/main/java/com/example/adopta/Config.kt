package com.example.adopta

object Config {
    const val URL_BASE = "http://10.0.2.2/adopta_api/" // IP con la que en emulador se refiere al local host
    const val URL_LOGIN = URL_BASE + "login.php" //Completar la URL según el archivo php, es como los endpoints
    const val URL_LISTAR = URL_BASE + "listar.php"
    const val URL_AGREGAR = URL_BASE + "agregar.php"
    const val URL_EDITAR = URL_BASE + "editar.php"
    const val URL_ELIMINAR = URL_BASE + "eliminar.php"
}
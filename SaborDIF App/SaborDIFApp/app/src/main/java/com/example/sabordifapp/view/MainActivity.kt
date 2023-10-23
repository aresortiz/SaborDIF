package com.example.sabordifapp.view

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.sabordifapp.R

//Verificacion de conectividad a internet
class MainActivity : AppCompatActivity() {

    //Metodo que se llama cuando la actividad se crea
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Verificar si la aplicación tiene acceso a Internet
        if (!isInternetAvailable()) {
            //Mostrar un AlertDialog indicando que no hay acceso a Internet
            showNoInternetDialog()
        }
    }

    //Funcion que verifica si la conexion a internet esta disponible
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    //Funcion que muestra un cuadro de dialogo personalizado cuando la app no tiene acceso a internet
    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
        builder.setTitle("Sin conexión a Internet")
            .setMessage("La aplicación no tiene acceso a Internet.\n\nPor favor, verifica tu conexión.")
            .setPositiveButton("OK") { dialog, _ ->
                // Cerrar el AlertDialog y, opcionalmente, salir de la aplicación
                dialog.dismiss()
                finish() // Esto cerrará la aplicación
            }
            .show()
    }
}
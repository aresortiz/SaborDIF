package com.example.sabordifapp.viewmodel.APIVM.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.sabordifapp.model.API.condicion.Condicion
import com.example.sabordifapp.model.API.condicion.CondicionAPI
import com.example.sabordifapp.model.API.condicion.RegistroCondicion
import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//ViewModel encargado de realizar solicitudes a la API relacionadas con condiciones y procesar las respuestas
class CondicionVM:ViewModel() {

    //Configuracion de retrofit para realizar solicitudes HTTP a la API
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://34.236.3.58:3000/api/condicion/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val descargarAPI by lazy {
        retrofit.create(CondicionAPI::class.java)
    }
    private val postearAPI by lazy {
        retrofit.create(CondicionAPI::class.java)
    }

    //Funcion que descarga una lista de condiciones desde la API
    fun descargarCondiciones(callback: (List<Condicion>?) -> Unit){
        val call = descargarAPI.descargarCondiciones()
        call.enqueue(object : Callback<List<Condicion>> {
            override fun onResponse(call: Call<List<Condicion>>, response: Response<List<Condicion>>){
                if(response.isSuccessful){
                    println("Lista de condiciones: ${response.body()}")
                    Log.d("API_TEST", "Lista de condiciones: ${response.body()}")
                    callback(response.body())
                }else{
                    Log.e("API_TEST", "FAILED")
                    callback(null)
                }
            }
            override fun onFailure(call: Call<List<Condicion>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                callback(null)
            }
        })
    }

    //Funcion que se utiliza para registrar una lista de condiciones en la API
    fun registrarCondicion(condiciones: List<RegistroCondicion>){
        for(condicion in condiciones){
            val call = postearAPI.registrarCondicion(condicion)
            call.enqueue(object : Callback<message> {
                override fun onResponse(call: Call<message>, response: Response<message>){
                    if(response.isSuccessful){
                        println("Se registro exitosamente la condicion: ${response.body()}")
                        Log.d("API_TEST", "Se registro exitosamente la de condicion: ${response.body()}")

                    }else{
                        Log.e("API_TEST", "FAILED")
                    }
                }
                //Se maneja cualquier error que pueda ocurrir durante la solicitud
                override fun onFailure(call: Call<message>, t: Throwable) {
                    println("ERROR: ${t.localizedMessage}")
                    Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                }
            })
        }

    }
}
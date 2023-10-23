package com.example.sabordifapp.viewmodel.APIVM.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.sabordifapp.model.API.comida.Comida
import com.example.sabordifapp.model.API.comida.ComidaAPI
import com.example.sabordifapp.model.API.comida.ComidaDependiente
import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//ViewModel encargado de realizar solicitudes a las APIs relacionadas con comidas y procesar las respuestas
class ComidaVM:ViewModel() {

    //Configuracion de retrofit para realizar solicitudes HTTP a la API
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://34.236.3.58:3000/api/comida/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val postearAPI by lazy {
        retrofit.create(ComidaAPI::class.java)
    }

    //Funcion para registrar una comida en la API
    fun registrarComida(comida: Comida){
        val call = postearAPI.registrarComida(comida)
        call.enqueue(object : Callback<message> {
            override fun onResponse(call: Call<message>, response: Response<message>) {
                if(response.isSuccessful) {
                    println("Comida registrada exitosamente: ${response.body()}")
                    Log.d("API_TEST", "Comida registrada exitosamente: ${response.body()}")
                }else{
                    Log.e("API_TEST", "FAILED")
                }
            }

            override fun onFailure(call: Call<message>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
            }
        })
    }

    //Funcion para registrar una lista de comidas de dependientes en la API
    fun registrarComidaDependiente(listaComidas: List<ComidaDependiente>){
        for(comida in listaComidas){
            val call = postearAPI.registrarComidaDependiente(comida)
            call.enqueue(object : Callback<message> {
                override fun onResponse(call: Call<message>, response: Response<message>) {
                    if(response.isSuccessful) {
                        println("Comida dependiente registrada exitosamente: ${response.body()}")
                        Log.d("API_TEST", "Comida dependiente registrada exitosamente: ${response.body()}")
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
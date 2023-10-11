package com.example.sabordifapp.viewmodel.APIVM.viewmodel

import android.util.Log
import com.example.sabordifapp.model.API.condicion.Condicion
import com.example.sabordifapp.model.API.condicion.CondicionAPI
import com.example.sabordifapp.model.API.condicion.RegistroCondicion
import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CondicionVM {
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
    fun descargarCondiciones(){
        val call = descargarAPI.descargarCondiciones()
        call.enqueue(object : Callback<List<Condicion>> {
            override fun onResponse(call: Call<List<Condicion>>, response: Response<List<Condicion>>){
                if(response.isSuccessful){
                    println("Lista de condiciones: ${response.body()}")
                    Log.d("API_TEST", "Lista de condiciones: ${response.body()}")

                }else{
                    Log.e("API_TEST", "FAILED")
                }
            }
            override fun onFailure(call: Call<List<Condicion>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
            }
        })
    }
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
                override fun onFailure(call: Call<message>, t: Throwable) {
                    println("ERROR: ${t.localizedMessage}")
                    Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                }
            })
        }

    }
}
package com.example.sabordifapp.viewmodel.APIVM.viewmodel

import android.util.Log
import com.example.sabordifapp.model.API.comensal.Comensal
import com.example.sabordifapp.model.API.comensal.ComensalAPI
import com.example.sabordifapp.model.API.comensal.ComensalId
import com.example.sabordifapp.model.API.comensal.ComensalRegistrar
import com.example.sabordifapp.model.API.comensal.Dependencia
import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ComensalVM {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://34.236.3.58:3000/api/comensal/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val descargarAPI by lazy {
        retrofit.create(ComensalAPI::class.java)
    }
    fun descargarComensales(){
        val call = descargarAPI.descargarComensales()
        call.enqueue(object : Callback<List<Comensal>> {
            override fun onResponse(call: Call<List<Comensal>>, response: Response<List<Comensal>>){
                if(response.isSuccessful){
                    println("Lista de comensales: ${response.body()}")
                    Log.d("API_TEST", "Lista de comensales: ${response.body()}")

                }else{
                    Log.e("API_TEST", "FAILED")
                }
            }
            override fun onFailure(call: Call<List<Comensal>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
            }
        })
    }

    fun descargarDependientes(idResponsable: Int){
        val call = descargarAPI.descargarDependientes(idResponsable)
        call.enqueue(object : Callback<List<Comensal>> {
            override fun onResponse(call: Call<List<Comensal>>, response: Response<List<Comensal>>){
                if(response.isSuccessful){
                    println("Lista de dependientes: ${response.body()}")
                    Log.d("API_TEST", "Lista de dependientes: ${response.body()}")

                }else{
                    Log.e("API_TEST", "FAILED")
                }
            }
            override fun onFailure(call: Call<List<Comensal>>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
            }
        })
    }
    private val postearAPI by lazy {
        retrofit.create(ComensalAPI::class.java)
    }
    fun registrarNuevoComensal(comensal: ComensalRegistrar){
        val call = postearAPI.registrarNuevoComensal(comensal)
        call.enqueue(object : Callback<ComensalId> {
            override fun onResponse(call: Call<ComensalId>, response: Response<ComensalId>) {
                if(response.isSuccessful) {
                    println("Comensal creado exitosamente: ${response.body()}")
                    Log.d("API_TEST", "Comensal creado exitosamente: ${response.body()}")
                }else{
                    Log.e("API_TEST", "FAILED")
                }
            }

            override fun onFailure(call: Call<ComensalId>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
            }
        })
    }
    fun registrarDependiente(dependencia: Dependencia){
        val call = postearAPI.registrarDependiente(dependencia)
        call.enqueue(object : Callback<message> {
            override fun onResponse(call: Call<message>, response: Response<message>) {
                if(response.isSuccessful) {
                    println("Dependencia creado exitosamente: ${response.body()}")
                    Log.d("API_TEST", "Dependencia creado exitosamente: ${response.body()}")
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
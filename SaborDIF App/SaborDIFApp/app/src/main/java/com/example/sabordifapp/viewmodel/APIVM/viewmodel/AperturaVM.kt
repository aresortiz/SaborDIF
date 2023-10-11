package com.example.sabordifapp.viewmodel.APIVM.viewmodel

import android.util.Log
import com.example.sabordifapp.model.API.apertura.Apertura
import com.example.sabordifapp.model.API.apertura.AperturaAPI
import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AperturaVM {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://34.236.3.58:3000/api/apertura/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val postearAPI by lazy {
        retrofit.create(AperturaAPI::class.java)
    }
    fun registrarApertura(apertura: Apertura){
        var call = postearAPI.registrarApertura(apertura)
        call.enqueue(object : Callback<message> {
            override fun onResponse(call: Call<message>, response: Response<message>) {
                if(response.isSuccessful) {
                    println("Apertura registrada exitosamente: ${response.body()}")
                    Log.d("API_TEST", "Apertura registrada exitosamente: ${response.body()}")
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
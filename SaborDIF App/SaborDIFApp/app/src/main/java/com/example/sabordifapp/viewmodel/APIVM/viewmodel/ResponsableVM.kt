package com.example.sabordifapp.viewmodel.APIVM.viewmodel

import android.util.Log
import com.example.sabordifapp.model.API.Access.Access
import com.example.sabordifapp.model.API.comedor.Comedor
import com.example.sabordifapp.model.API.comedor.ComedorAPI
import com.example.sabordifapp.model.API.responsable.Responsable
import com.example.sabordifapp.model.API.responsable.ResponsableAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResponsableVM {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://34.236.3.58:3000/api/responsable/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val postearAPI by lazy {
        retrofit.create(ResponsableAPI::class.java)
    }
    fun validarLogin(responsable: Responsable, callback: (Access?) -> Unit){
        val call = postearAPI.validarLogin(responsable)
        call.enqueue(object : Callback<Access> {
            override fun onResponse(call: Call<Access>, response: Response<Access>) {
                if(response.isSuccessful) {
                    println("El login es: ${response.body()}")
                    Log.d("API_TEST", "Apertura registrada exitosamente: ${response.body()}")
                    callback(response.body())
                }else{
                    Log.e("API_TEST", "FAILED")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Access>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                Log.e("API_TEST", "FAILED ${t.localizedMessage}")
                callback(null)
            }
        })
    }
}
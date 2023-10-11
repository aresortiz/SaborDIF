package com.example.sabordifapp.model.API.comida

import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ComidaAPI {
    @POST("registrar")
    fun registrarComida(@Body comida: Comida): Call<message>
    @POST("registrar/dependiente")
    fun registrarComidaDependiente(@Body comida: ComidaDependiente): Call<message>
}
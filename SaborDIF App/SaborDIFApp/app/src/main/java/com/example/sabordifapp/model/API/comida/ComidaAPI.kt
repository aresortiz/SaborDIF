package com.example.sabordifapp.model.API.comida

import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//Interfaz que define las operaciones de la API relacionadas con las comidas
interface ComidaAPI {
    @POST("registrar")
    //Solicitud POST para registrar una comida
    fun registrarComida(@Body comida: Comida): Call<message>
    @POST("registrar/dependiente")
    //Solicitud POST para registrar una comida de un dependiente
    fun registrarComidaDependiente(@Body comida: ComidaDependiente): Call<message>
}
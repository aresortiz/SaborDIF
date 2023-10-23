package com.example.sabordifapp.model.API.condicion

import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//Interfaz que define las operaciones de la API relacionadas con las condiciones
interface CondicionAPI {
    @GET("todas")
    //Solicitud GET para descargar todas las condiciones
    fun descargarCondiciones(): Call<List<Condicion>>

    @POST("registrar")
    //Solicitud POST para registrar una nueva condicion
    fun registrarCondicion(@Body condicion: RegistroCondicion): Call<message>

}
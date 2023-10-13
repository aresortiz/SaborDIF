package com.example.sabordifapp.model.API.condicion

import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CondicionAPI {
    @GET("todas")
    fun descargarCondiciones(): Call<List<Condicion>>

    @POST("registrar")
    fun registrarCondicion(@Body condicion: RegistroCondicion): Call<message>

}
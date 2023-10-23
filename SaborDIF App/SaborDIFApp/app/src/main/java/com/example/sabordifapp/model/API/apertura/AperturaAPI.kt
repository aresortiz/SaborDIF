package com.example.sabordifapp.model.API.apertura

import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//Interfaz que define las operacines de la API relacionadas con la apertura de comedores
interface AperturaAPI {
    @POST("registrar")
    //El metodo se llama mediante una solicitud POST a la ruta registrar
    fun registrarApertura(@Body apertura: Apertura): Call<message>
}

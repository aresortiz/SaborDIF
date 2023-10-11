package com.example.sabordifapp.model.API.apertura

import mx.rmr.sabor_dif_api.model.responses.message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AperturaAPI {
    @POST("registrar")
    fun registrarApertura(@Body apertura: Apertura): Call<message>
}

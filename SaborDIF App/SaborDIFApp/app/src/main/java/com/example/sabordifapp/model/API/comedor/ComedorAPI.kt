package com.example.sabordifapp.model.API.comedor

import retrofit2.Call
import retrofit2.http.GET

interface ComedorAPI {
    @GET("comedor/nombres")
    fun descargarNombresComedor(): Call<List<Comedor>>
}
package com.example.sabordifapp.model.API.comedor

import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ComedorAPI {
    @GET("comedor/nombres")
    fun descargarNombresComedor(): Call<List<Comedor>>
    @POST("vendidasDonadas/{idComedor}")
    fun descargarVendidasDonadas(@Path("idComedor") idComedor: Int): Call<ComidasVendidas>
}
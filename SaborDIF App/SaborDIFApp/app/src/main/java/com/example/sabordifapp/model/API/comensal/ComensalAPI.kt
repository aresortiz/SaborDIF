package com.example.sabordifapp.model.API.comensal

import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ComensalAPI {
    @GET("todos")
    fun descargarComensales(): Call<List<Comensal>>
    @GET("dependientes/{idResponsable}")
    fun descargarDependientes(@Path("idResponsable") idResponsable: Int): Call<List<Comensal>>


    @POST("registrar")
    fun registrarNuevoComensal(@Body comensal: ComensalRegistrar): Call<ComensalId>
    @POST("registrar/dependiente")
    fun registrarDependiente(@Body dependencia: Dependencia): Call<message>

}
package com.example.sabordifapp.model.API.comensal

import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

//Interfaz que define las operaciones de la API relacionadas con los comensales
interface ComensalAPI {
    @GET("todos")
    //Solicitud GET para descargar todos los comensales
    fun descargarComensales(): Call<List<Comensal>>
    @GET("dependientes/{idResponsable}")
    //Solicitud GET para descargar todos los dependientes
    fun descargarDependientes(@Path("idResponsable") idResponsable: Int): Call<List<Comensal>>


    @POST("registrar")
    //Solicitud POST para registrar un nuevo comensal
    fun registrarNuevoComensal(@Body comensal: ComensalRegistrar): Call<ComensalId>
    @POST("registrar/dependiente")
    //Solicitud POST para registrar un nuevo dependiente
    fun registrarDependiente(@Body dependencia: Dependencia): Call<message>

}
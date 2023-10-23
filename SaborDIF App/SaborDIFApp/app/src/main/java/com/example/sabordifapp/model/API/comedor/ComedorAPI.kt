package com.example.sabordifapp.model.API.comedor

import com.example.sabordifapp.model.responses.message
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

//Interfaz que define las operaciones de la API relacionadas con los comedores
interface ComedorAPI {
    @GET("comedor/nombres")
    //Solicitud GET a la ruta comedor/nombres para descargar los nombres de los comedores
    fun descargarNombresComedor(): Call<List<Comedor>>
    @POST("vendidasDonadas/{idComedor}")
    //Solicitud POST para obtener la información sobre comidas vendidas y donadas en un comedor especifico
    fun descargarVendidasDonadas(@Path("idComedor") idComedor: Int): Call<ComidasVendidas>
}
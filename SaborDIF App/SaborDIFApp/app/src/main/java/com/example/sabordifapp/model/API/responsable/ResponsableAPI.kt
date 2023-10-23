package com.example.sabordifapp.model.API.responsable

import com.example.sabordifapp.model.API.Access.Access
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//Interfaz que define las operaciones de la API relacionadas con los responsables
interface ResponsableAPI {
    @POST("responsable/login")
    //Solicitud POST para validar el inicio de sesion de un responsable
    fun validarLogin(@Body responsable: Responsable): Call<Access>
}
package com.example.sabordifapp.model.API.responsable

import com.example.sabordifapp.model.API.Access.Access
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ResponsableAPI {
    @POST("responsable/login")
    fun validarLogin(@Body responsable: Responsable): Call<Access>
}
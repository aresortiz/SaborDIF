package com.example.sabordifapp.model.API.responsable

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para un responsable
data class Responsable(
    //Campo que almacena el ID del comedor
    @SerializedName("idComedor") var idComedor: Int,
    //Campo que almacena la contraseña del responsable
    @SerializedName("pswd") var pswd: String
)

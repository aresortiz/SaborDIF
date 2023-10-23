package com.example.sabordifapp.model.API.comensal

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para la dependendencia entre comensales
data class Dependencia(
    //Campo que almacena el ID del comensal dependiente
    @SerializedName("idDepende") var idDepende: Int,
    //Campo que almacena el ID del comensal responsable
    @SerializedName("idDependiente") var idDependiente: Int
)

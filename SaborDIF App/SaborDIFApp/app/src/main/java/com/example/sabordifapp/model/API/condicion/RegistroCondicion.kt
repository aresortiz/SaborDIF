package com.example.sabordifapp.model.API.condicion

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para registrar una condicion
data class RegistroCondicion(
    //Campo que almacena el ID del comensal
    @SerializedName("idComensal") var idComensal: Int,
    //Campo que almacena el ID de la condicion
    @SerializedName("idCondicion") var idCondicion: Int
)

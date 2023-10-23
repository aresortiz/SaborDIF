package com.example.sabordifapp.model.API.comensal
import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para el ID de un comensal
data class ComensalId(
    //Campo que almacena el ID del comensal
    @SerializedName("IdComensal") var idComensal: Int
)

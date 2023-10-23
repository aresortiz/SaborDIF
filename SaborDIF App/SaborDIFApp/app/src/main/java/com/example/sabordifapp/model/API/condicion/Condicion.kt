package com.example.sabordifapp.model.API.condicion

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para una condicion
data class Condicion(
    //Campo que almacena el ID de la condición
    @SerializedName("IdCondicion") var IdCondicion: Int,
    //Campo que almacena el nombre de la condicion
    @SerializedName("nombreCondicion") var nombreCondicion: String
)

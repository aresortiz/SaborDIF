package com.example.sabordifapp.model.API.apertura

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para la apertura de un comedor
data class Apertura(
    //Se mapea a la clave idComedor en la respuesta JSON
    @SerializedName("idComedor") var idComedor: Int,
    //Se mapea a la clave abierto en la respuesta JSON
    @SerializedName("abierto") var abierto: Int
)

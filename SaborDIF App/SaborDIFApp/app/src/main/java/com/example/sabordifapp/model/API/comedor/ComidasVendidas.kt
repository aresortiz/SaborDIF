package com.example.sabordifapp.model.API.comedor

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para el total de comidas vendidas y donadas en un comedor
data class ComidasVendidas(
    //Campo que almacena el total de comidades vendidas
    @SerializedName("TotalComidasVendidas") val totalComidasVendidas: Int,
    //Campo que almacena el total de comidad donadas
    @SerializedName("TotalComidasDonadas") val totalComidasDonadas: Int
)

package com.example.sabordifapp.model.API.comensal

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para registrar un nuevo comensal
//Contiene campos para almacenar el nombre, apellido paterno, apellido materno, CURP  y género del comensal
data class ComensalRegistrar(
    @SerializedName("nombreComensal") var nombreComensal: String,
    @SerializedName("apellidoPaterno") var apellidoPaterno: String,
    @SerializedName("apellidoMaterno") var apellidoMatenro: String,
    @SerializedName("curp") var curp: String,
    @SerializedName("genero") var genero: Int
)
//0 = femenino
//1 = masculino
//2 = prefiero no decirlo
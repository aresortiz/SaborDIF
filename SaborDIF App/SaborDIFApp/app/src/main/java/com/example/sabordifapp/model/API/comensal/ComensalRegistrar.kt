package com.example.sabordifapp.model.API.comensal

import com.google.gson.annotations.SerializedName

data class ComensalRegistrar(
    @SerializedName("nombreComensal") var nombreComensal: String,
    @SerializedName("apellidoPaterno") var apellidoPaterno: String,
    @SerializedName("apellidoMaterno") var apellidoMatenro: String,
    @SerializedName("curp") var curp: String,
    @SerializedName("genero") var genero: Int
)

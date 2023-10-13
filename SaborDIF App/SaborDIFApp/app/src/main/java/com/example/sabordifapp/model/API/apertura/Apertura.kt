package com.example.sabordifapp.model.API.apertura

import com.google.gson.annotations.SerializedName

data class Apertura(
    @SerializedName("idComedor") var idComedor: Int,
    @SerializedName("abierto") var abierto: Int
)

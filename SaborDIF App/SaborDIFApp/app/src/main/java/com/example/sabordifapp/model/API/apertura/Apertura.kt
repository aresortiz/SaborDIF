package com.example.sabordifapp.model.API.apertura

import com.google.gson.annotations.SerializedName

data class Apertura(
    @SerializedName("nombreComedor") var nombreComedor: String,
    @SerializedName("abierto") var abierto: Int
)

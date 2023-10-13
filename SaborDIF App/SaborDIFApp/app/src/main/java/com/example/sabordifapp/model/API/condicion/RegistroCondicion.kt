package com.example.sabordifapp.model.API.condicion

import com.google.gson.annotations.SerializedName

data class RegistroCondicion(
    @SerializedName("idComensal") var idComensal: Int,
    @SerializedName("idCondicion") var idCondicion: Int
)

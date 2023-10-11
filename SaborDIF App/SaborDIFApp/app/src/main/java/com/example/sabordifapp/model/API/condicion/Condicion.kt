package com.example.sabordifapp.model.API.condicion

import com.google.gson.annotations.SerializedName

data class Condicion(
    @SerializedName("IdCondicion") var IdCondicion: Int,
    @SerializedName("nombreCondicion") var nombreCondicion: String
)

package com.example.sabordifapp.model.API.comensal

import com.google.gson.annotations.SerializedName

data class Dependencia(
    @SerializedName("idDepende") var idDepende: Int,
    @SerializedName("idDependiente") var idDependiente: Int
)

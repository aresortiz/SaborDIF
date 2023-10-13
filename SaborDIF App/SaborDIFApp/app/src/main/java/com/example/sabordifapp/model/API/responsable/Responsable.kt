package com.example.sabordifapp.model.API.responsable

import com.google.gson.annotations.SerializedName

data class Responsable(
    @SerializedName("idComedor") var idComedor: Int,
    @SerializedName("pswd") var pswd: String
)

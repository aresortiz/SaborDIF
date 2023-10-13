package com.example.sabordifapp.model.API.comida

import com.google.gson.annotations.SerializedName

data class Comida(
   @SerializedName("idComensal") var idComensal: Int,
   @SerializedName("idComedor") var idComedor: Int,
   @SerializedName("aportacion") var aportacion: Int,
   @SerializedName("paraLlevar") var paraLlevar: Int
)

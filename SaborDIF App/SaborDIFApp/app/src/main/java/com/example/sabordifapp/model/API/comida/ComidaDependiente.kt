package com.example.sabordifapp.model.API.comida
import com.google.gson.annotations.SerializedName

data class ComidaDependiente(
    @SerializedName("idComedor") var idComedor: Int,
    @SerializedName("idDependiente") var idDependient: Int,
    @SerializedName("idDepende") var idDepende: Int,
    @SerializedName("aportacion") var aportacion: Int,
    @SerializedName("paraLlevar") var paraLlevar: Int
   // var listaDeComidasDependientes: MutableList<ComidaDependiente> = mutableListOf<ComidaDependiente>()
)

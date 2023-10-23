package com.example.sabordifapp.model.API.comida

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para registrar informacion sobre comidas
data class Comida(
   //Campo que almacema el ID del comensal
   @SerializedName("idComensal") var idComensal: Int,
   //Campo que almacema el ID del comedor
   @SerializedName("idComedor") var idComedor: Int,
   //Campo que almacema la aportacion
   @SerializedName("aportacion") var aportacion: Int,
   //Campo que almacema si la comida es para llevar
   @SerializedName("paraLlevar") var paraLlevar: Int
)

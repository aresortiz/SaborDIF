package com.example.sabordifapp.model.API.comedor

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para un comedor y contiene
// informacion sobre su nombre y su identificador
data class Comedor(
    //Campo que almacena el nombre del comedor
    @SerializedName("NombreComedor") var nombreComedor: String,
    //Campo que almacena el identificador del comedor
    @SerializedName("IdComedor") var IdComedor: Int
)

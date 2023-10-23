package com.example.sabordifapp.model.responses

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para un mensake
data class message(
    //Campo que almacena el mensaje
    @SerializedName("message") var message: String
)

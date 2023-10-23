package com.example.sabordifapp.model.API.Access

import com.google.gson.annotations.SerializedName

//Clase que representa un modelo de datos para el acceso a una entidad
data class Access(
    //Este campo se mapea a la clave access en la respuesta JSON
    @SerializedName("access") var access: Int
)

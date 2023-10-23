package com.example.sabordifapp.model.API.comensal

import com.google.gson.annotations.SerializedName

/**
 * "IdComensal": 1,
 *     "nombres": "Miguel Angel",
 * "apellidoPaterno": "Figueroa",
 * "apellidoMaterno": "Andrade"
 */

//Clase que representa un modelo de datos para un comensal e incluye su ID, nombres, apellido paterno y materno
data class Comensal(
    //Campo que almacema el ID del comensal
    @SerializedName("IdComensal") var IdComensal: Int,
    //Campo que almacema el nombre/s y apellidos del comensal
    @SerializedName("nombres") var nombres: String,
    @SerializedName("apellidoPaterno") var apellidoPaterno: String,
    @SerializedName("apellidoMaterno") var apellidoMatenro: String
)

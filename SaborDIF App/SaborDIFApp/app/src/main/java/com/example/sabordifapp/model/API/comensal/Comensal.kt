package com.example.sabordifapp.model.API.comensal

import com.google.gson.annotations.SerializedName

/**
 * "IdComensal": 1,
 *     "nombres": "Miguel Angel",
 * "apellidoPaterno": "Figueroa",
 * "apellidoMaterno": "Andrade"
 */
data class Comensal(
    @SerializedName("IdComensal") var IdComensal: Int,
    @SerializedName("nombres") var nombres: String,
    @SerializedName("apellidoPaterno") var apellidoPaterno: String,
    @SerializedName("apellidoMaterno") var apellidoMatenro: String
)

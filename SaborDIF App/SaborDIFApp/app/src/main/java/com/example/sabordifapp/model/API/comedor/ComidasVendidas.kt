package com.example.sabordifapp.model.API.comedor

import com.google.gson.annotations.SerializedName

data class ComidasVendidas(
    @SerializedName("TotalComidasVendidas") val totalComidasVendidas: Int,
    @SerializedName("TotalComidasDonadas") val totalComidasDonadas: Int
)

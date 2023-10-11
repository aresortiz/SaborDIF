package com.example.sabordifapp.model.responses

import com.google.gson.annotations.SerializedName

data class message(
    @SerializedName("message") var message: String
)

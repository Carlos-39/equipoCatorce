package com.example.equipocatorce.model

import com.google.gson.annotations.SerializedName

data class DogImageResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)

package com.example.equipocatorce.network

data class DogBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)
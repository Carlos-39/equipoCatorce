package com.example.equipocatorce.network

import retrofit2.Call
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/list/all")
    fun getAllBreeds(): Call<DogBreedsResponse>
}
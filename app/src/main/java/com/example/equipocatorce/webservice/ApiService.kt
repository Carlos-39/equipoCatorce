//package com.example.equipocatorce.webservice
//
//import com.example.equipocatorce.model.DogBreedResponse
//import com.example.equipocatorce.model.DogImageResponse
//import com.example.equipocatorce.utils.Constants
//import retrofit2.http.GET
//import retrofit2.http.Path
//
//interface ApiService {
//    @GET(Constants.END_POINT_BREED)
//    suspend fun getBreeds(): DogBreedResponse
//
//    @GET(Constants.END_POINT_IMAGE)
//    suspend fun getImage(@Path("breed") breed: String): DogImageResponse
//}
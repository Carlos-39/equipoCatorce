package com.example.equipocatorce.repository

import android.content.Context
import com.example.equipocatorce.data.DogAppointmentDao
import com.example.equipocatorce.data.DogAppointmentsDB
import com.example.equipocatorce.model.DogAppointment
import com.example.equipocatorce.model.DogImageResponse
import com.example.equipocatorce.webservice.ApiService
import com.example.equipocatorce.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogAppointmentRepository(val context: Context) {
    private var dogAppointmentsDao: DogAppointmentDao = DogAppointmentsDB.getDatabase(context).dogAppointmentsDao()
    private var apiService: ApiService = ApiUtils.getApiService()

    // DogAppointments configuration

    // Obtener citas
    suspend fun getAppointments():MutableList<DogAppointment>{
        return withContext(Dispatchers.IO){
            dogAppointmentsDao.getAppointments()
        }
    }

    // Guardar cita
    suspend fun saveAppointment(dogAppointment: DogAppointment) {
        withContext(Dispatchers.IO){
            dogAppointmentsDao.saveAppointment(dogAppointment)
        }
    }

    // Borrar cita
    suspend fun deleteAppointment(dogAppointment: DogAppointment) {
        withContext(Dispatchers.IO){
            dogAppointmentsDao.deleteAppointment(dogAppointment)
        }
    }

    // Actualizar cita
    suspend fun updateAppointment(dogAppointment: DogAppointment) {
        withContext(Dispatchers.IO){
            dogAppointmentsDao.updateAppointment(dogAppointment)
        }
    }

    // API service configuration

    suspend fun getBreeds(): Map<String, List<String>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getBreeds()
                response.message
            } catch (e: Exception) {
                e.printStackTrace()
                emptyMap<String, List<String>>()
            }
        }
    }

    suspend fun getImage(): DogImageResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getImage()
                response
            } catch (e: Exception) {
                e.printStackTrace()
                DogImageResponse(message = "", status = "")
            }
        }
    }
}
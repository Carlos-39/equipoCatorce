package com.example.equipocatorce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.equipocatorce.model.DogAppointment
import com.example.equipocatorce.model.DogImageResponse
import com.example.equipocatorce.repository.DogAppointmentRepository
import kotlinx.coroutines.launch

// ViewModel que actúa como intermediario entre la UI y el repositorio de datos.
class DogAppointmentViewModel(application: Application): AndroidViewModel(application) {
    val context = getApplication<Application>()

    private val dogAppointmentRepository = DogAppointmentRepository(context)

    // LiveData que contiene la lista de citas
    private val _dogAppointments = MutableLiveData<MutableList<DogAppointment>>()
    val dogAppointments: LiveData<MutableList<DogAppointment>> get() = _dogAppointments

    // LiveData para representar el estado de carga (loading)
    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    // LiveData con el listado de razas de perro desde la API
    private val _dogBreedList = MutableLiveData<Map<String, List<String>>> ()
    val dogBreedList: LiveData<Map<String, List<String>>> get() = _dogBreedList

    // LiveData que contiene la respuesta de imagen del perro según raza
    private val _dogImage = MutableLiveData<DogImageResponse>()
    val dogImage: LiveData<DogImageResponse> get() = _dogImage

    // LiveData que contiene una cita seleccionada (por id)
    private val _selectedAppointment = MutableLiveData<DogAppointment?>()
    val selectedAppointment: LiveData<DogAppointment?> get() = _selectedAppointment

    // Guarda una nueva cita o reemplaza una existente.
    fun saveAppointment(dogAppointment: DogAppointment) {
        viewModelScope.launch {
            _progresState.value = true

            try {
                dogAppointmentRepository.saveAppointment(dogAppointment)
                _progresState.value = false
            } catch (e:Exception) {
                _progresState.value = false
            }
        }
    }

    // Obtiene todas las citas guardadas en la base de datos local.
    fun getAppointments() {
        viewModelScope.launch {
            _progresState.value = true

            try {
                _dogAppointments.value = dogAppointmentRepository.getAppointments()
                _progresState.value = false
            } catch (e:Exception) {
                _progresState.value = false
            }
        }
    }

    // Elimina una cita específica.
    fun deleteAppointment(dogAppointment: DogAppointment) {
        viewModelScope.launch {
            _progresState.value = true

            try {
                dogAppointmentRepository.deleteAppointment(dogAppointment)
                _progresState.value = false
            } catch (e:Exception) {
                _progresState.value = false
            }
        }
    }

    // Actualiza una cita existente.
    fun updateAppointment(dogAppointment: DogAppointment) {
        viewModelScope.launch {
            _progresState.value = true

            try {
                dogAppointmentRepository.updateAppointment(dogAppointment)
                _progresState.value = false
            } catch (e:Exception) {
                _progresState.value = false
            }
        }
    }

    //Obtiene la lista de razas de perro desde la API
    fun getBreeds() {
        viewModelScope.launch {
            _progresState.value = true

            try {
                _dogBreedList.value = dogAppointmentRepository.getBreeds()
                _progresState.value = false
            } catch (e:Exception) {
                _progresState.value = false
            }
        }
    }

    // Obtiene una imagen aleatoria para la raza de perro especificada.
    fun getImage(breed: String) {
        viewModelScope.launch {
            _progresState.value = true

            try {
                _dogImage.value = dogAppointmentRepository.getImage(breed)
                _progresState.value = false
            } catch (e:Exception) {
                _progresState.value = false
            }
        }
    }

    // Obtiene una cita específica desde la base de datos local usando su ID.
    fun getAppointmentById(id: Int) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                _selectedAppointment.value = dogAppointmentRepository.getAppointmentById(id)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
                _selectedAppointment.value = null
            }
        }
    }
}
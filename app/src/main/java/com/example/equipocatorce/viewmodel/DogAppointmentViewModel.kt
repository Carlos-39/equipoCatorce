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

class DogAppointmentViewModel(application: Application): AndroidViewModel(application) {
    val context = getApplication<Application>()

    private val dogAppointmentRepository = DogAppointmentRepository(context)

    private val _dogAppointments = MutableLiveData<MutableList<DogAppointment>>()
    val dogAppointments: LiveData<MutableList<DogAppointment>> get() = _dogAppointments

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    private val _dogBreedList = MutableLiveData<Map<String, List<String>>> ()
    val dogBreedList: LiveData<Map<String, List<String>>> get() = _dogBreedList

    private val _dogImage = MutableLiveData<DogImageResponse>()
    val dogImage: LiveData<DogImageResponse> get() = _dogImage

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
}
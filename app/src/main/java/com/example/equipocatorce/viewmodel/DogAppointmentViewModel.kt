//package com.example.equipocatorce.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.example.equipocatorce.model.DogAppointment
//import com.example.equipocatorce.repository.DogAppointmentRepository
//
//class DogAppointmentViewModel(application: Application): AndroidViewModel(application) {
//    val context = getApplication<Application>()
//
//    private val dogAppointmentRepository = DogAppointmentRepository(context)
//
//    private val _dogAppointments = MutableLiveData<MutableList<DogAppointment>>()
//    val dogAppointments: LiveData<MutableList<DogAppointment>> get() = _dogAppointments
//
//    private val _progresState = MutableLiveData(false)
//    val progresState: LiveData<Boolean> = _progresState
//
//    // Almacenar lista de razas (TODO)
//
//}
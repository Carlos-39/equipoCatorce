package com.example.equipocatorce.view.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.equipocatorce.databinding.DogAppointmentItemBinding
import com.example.equipocatorce.model.DogAppointment
import com.example.equipocatorce.view.fragment.HomeFragmentDirections

class DogAppointmentViewHolder(
    private val binding: DogAppointmentItemBinding,
    private val navController: androidx.navigation.NavController // Controlador de navegación para mover entre pantallas
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun setAppointmentItem(dogAppointment: DogAppointment) {
        // Muestra el nombre de la mascota
        binding.dogName.text = dogAppointment.dogName
        // Muestra el síntoma que presenta la mascota
        binding.dogSymptom.text = dogAppointment.dogSymptom
        // Muestra el turno de atención (ID) con un prefijo #
        binding.id.text = "#" + dogAppointment.id

        // Carga la imagen circular de la mascota desde una URL o recurso con Glide
        Glide.with(binding.root.context).load(dogAppointment.dogImage).into(binding.dogImage)

        // Configura un clic sobre el ítem completo
        binding.root.setOnClickListener {
            // Usa SafeArgs para navegar al fragmento de detalle pasando el ID de la cita
            val action = HomeFragmentDirections.actionHomeFragmentToDetailAppointment(dogAppointment.id)
            navController.navigate(action)
        }
    }
}
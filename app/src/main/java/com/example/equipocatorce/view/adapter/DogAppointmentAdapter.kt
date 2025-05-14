package com.example.equipocatorce.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.equipocatorce.databinding.DogAppointmentItemBinding
import com.example.equipocatorce.view.viewholder.DogAppointmentViewHolder
import com.example.equipocatorce.model.DogAppointment

// Adaptador del RecyclerView para mostrar citas de perros
class DogAppointmentAdapter(private val appointmentsList:MutableList<DogAppointment>, private val navController: NavController):
    RecyclerView.Adapter<DogAppointmentViewHolder>() {

    // Se llama cuando se necesita crear un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogAppointmentViewHolder {
        val binding = DogAppointmentItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return DogAppointmentViewHolder(binding, navController)
    }

    // Retorna el tamaño de la lista (cuántos elementos mostrará el RecyclerView)
    override fun getItemCount(): Int {
        return appointmentsList.size
    }

    // Asocia los datos de una cita con el ViewHolder correspondiente
    override fun onBindViewHolder(holder: DogAppointmentViewHolder, position: Int) {
        val appointment = appointmentsList[position] // Obtiene la cita según la posición
        holder.setAppointmentItem(appointment)
    }
}
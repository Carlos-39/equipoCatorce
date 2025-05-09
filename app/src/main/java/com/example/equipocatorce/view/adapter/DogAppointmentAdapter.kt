package com.example.equipocatorce.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.equipocatorce.databinding.DogAppointmentItemBinding
import com.example.equipocatorce.view.viewholder.DogAppointmentViewHolder
import com.example.equipocatorce.model.DogAppointment

class DogAppointmentAdapter(private val appointmentsList:MutableList<DogAppointment>, private val navController: NavController):
    RecyclerView.Adapter<DogAppointmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogAppointmentViewHolder {
        val binding = DogAppointmentItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return DogAppointmentViewHolder(binding, navController)
    }

    override fun getItemCount(): Int {
        return appointmentsList.size
    }

    override fun onBindViewHolder(holder: DogAppointmentViewHolder, position: Int) {
        val appointment = appointmentsList[position]
        holder.setAppointmentItem(appointment)
    }
}
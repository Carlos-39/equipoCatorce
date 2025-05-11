package com.example.equipocatorce.view.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.equipocatorce.databinding.DogAppointmentItemBinding
import com.example.equipocatorce.model.DogAppointment
import com.example.equipocatorce.view.fragment.HomeFragmentDirections

class DogAppointmentViewHolder(
    private val binding: DogAppointmentItemBinding,
    private val navController: androidx.navigation.NavController
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun setAppointmentItem(dogAppointment: DogAppointment) {
        binding.dogName.text = dogAppointment.dogName
        binding.dogSymptom.text = dogAppointment.dogSymptom
        binding.id.text = "#" + dogAppointment.id
        Glide.with(binding.root.context).load(dogAppointment.dogImage).into(binding.dogImage)

        //SafeArgs para pasar ID
        binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailAppointment(dogAppointment.id)
            navController.navigate(action)
        }
    }
}
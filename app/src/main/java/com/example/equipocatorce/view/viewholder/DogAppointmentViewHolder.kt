package com.example.equipocatorce.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.equipocatorce.R
import com.example.equipocatorce.databinding.DogAppointmentItemBinding
import com.example.equipocatorce.model.DogAppointment

class DogAppointmentViewHolder(binding: DogAppointmentItemBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root){
    val bindingItem = binding
    val navController = navController

    fun setAppointmentItem(dogAppointment: DogAppointment) {
        bindingItem.dogName.text = dogAppointment.dogName
        bindingItem.dogSymptom.text = dogAppointment.dogSymptom
        bindingItem.id.text = "# ${dogAppointment.id}"
        Glide.with(bindingItem.root.context).load(dogAppointment.dogImage).into(bindingItem.dogImage)

        bindingItem.dateItem.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("dogAppointment", dogAppointment)
            navController.navigate(R.id.action_homeFragment_to_nuevaCitaFragment, bundle)
        }
    }
}
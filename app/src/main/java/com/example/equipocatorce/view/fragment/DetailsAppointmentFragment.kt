package com.example.equipocatorce.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.equipocatorce.databinding.FragmentDetailsAppointmentBinding
import com.example.equipocatorce.view.fragment.DetailsAppointmentFragmentDirections
import com.example.equipocatorce.model.DogAppointment
import com.example.equipocatorce.viewmodel.DogAppointmentViewModel


class DetailsAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentDetailsAppointmentBinding
    private val args: DetailsAppointmentFragmentArgs by navArgs()
    private val viewModel: DogAppointmentViewModel by viewModels()
    private var currentAppointment: DogAppointment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsAppointmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAppointmentDetails()
        setupListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun getAppointmentDetails(){
        val appointmentId = args.appointmentId

        viewModel.getAppointmentById(appointmentId)
        viewModel.selectedAppointment.observe(viewLifecycleOwner) { appointment ->
            if (appointment != null) {
                currentAppointment = appointment
                binding.tvId.text = "#" + appointment.id.toString()
                binding.tvDogName.text = appointment.dogName
                binding.tvDogBreed.text = appointment.dogBreed
                binding.tvDogSymptom.text = appointment.dogSymptom
                binding.tvOwnerName.text = "Propietario: " + appointment.ownerName
                binding.tvPhone.text = "Telefono: " + appointment.phone
                Glide.with(requireContext()).load(appointment.dogImage).into(binding.imageTopCard)
            } else {
                Toast.makeText(requireContext(), "Cita no encontrada", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.fabEdit.setOnClickListener{
            editAppointment()
        }

        binding.fabDelete.setOnClickListener{
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar esta cita?")
        builder.setPositiveButton("Eliminar") { _, _ ->
            deleteAppointment()
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun editAppointment() {
        currentAppointment?.let {
            val action = DetailsAppointmentFragmentDirections
                .actionDetailAppointmentToEditAppointmentFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun deleteAppointment() {
        currentAppointment?.let {
            viewModel.deleteAppointment(it)
            Toast.makeText(requireContext(), "Cita eliminada", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }
}
package com.example.equipocatorce.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.equipocatorce.databinding.FragmentEditAppointmentBinding
import com.example.equipocatorce.model.DogAppointment
import com.example.equipocatorce.viewmodel.DogAppointmentViewModel

class EditAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentEditAppointmentBinding
    private val dogAppointmentViewModel: DogAppointmentViewModel by viewModels()
    private lateinit var receivedAppointment: DogAppointment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAppointmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getData()
//        observers()
//        controladores()
    }

    private fun getData() {
        val args = arguments
        receivedAppointment = args?.getSerializable("dataAppointment") as DogAppointment

        binding.tiNombreMascota.editText?.setText(receivedAppointment.dogName)
        binding.tiNombrePropietario.editText?.setText(receivedAppointment.ownerName)
        binding.tiTelefono.editText?.setText(receivedAppointment.phone)
        binding.tiRaza.editText?.setText(receivedAppointment.dogBreed)

        dogAppointmentViewModel.getImage()
        dogAppointmentViewModel.getBreeds()
    }

    private fun observers() {
        dogAppointmentViewModel.getBreeds()
        dogAppointmentViewModel.dogBreedList.observe(viewLifecycleOwner) { breeds ->
            val breedsList: MutableList<String> = mutableListOf()

            for ((breed, variants) in breeds) {
                if(variants.isEmpty()) {
                    breedsList.add(breed.replaceFirstChar { it.titlecase() })
                } else {
                    for (variant in variants) {
                        breedsList.add("${breed.replaceFirstChar { it.titlecase() }} ${variant.replaceFirstChar { it.titlecase() }}")
                    }
                }
            }

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breedsList)
            binding.autoInputRaza.setAdapter(adapter)
        }
    }

    private fun controladores() {
        // Botón de Atras TODO


        // TextWatcher para validación dinámica
        val watcher = {
            val name = binding.tiNombreMascota.editText?.text.toString().trim()
            val breed = binding.tiRaza.editText?.text.toString().trim()
            val ownerName = binding.tiNombrePropietario.editText?.text.toString().trim()
            val phone = binding.tiTelefono.editText?.text.toString().trim()

            binding.btnEditarCita.isEnabled = name.isNotEmpty() && ownerName.isNotEmpty() && breed.isNotEmpty() && phone.isNotEmpty()
        }

        listOf(
            binding.tiNombreMascota.editText,
            binding.tiRaza.editText,
            binding.tiNombrePropietario.editText,
            binding.tiTelefono.editText
        ).forEach { it?.addTextChangedListener { watcher() }}

        // Acción del botón "Editar"
        binding.btnEditarCita.setOnClickListener {
            val update = DogAppointment(
                id = receivedAppointment.id,
                dogName = binding.tiNombreMascota.editText?.text.toString(),
                dogBreed = binding.tiRaza.editText?.text.toString(),
                ownerName = binding.tiNombrePropietario.editText?.text.toString(),
                phone = binding.tiTelefono.editText?.text.toString(),
                dogSymptom = receivedAppointment.dogSymptom,
                dogImage = receivedAppointment.dogImage
            )

            dogAppointmentViewModel.updateAppointment(update)
            Toast.makeText(requireContext(), "Ir a HU2", Toast.LENGTH_SHORT).show() //TODO
        }
    }
}
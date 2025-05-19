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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.equipocatorce.R
import com.example.equipocatorce.databinding.FragmentEditAppointmentBinding
import com.example.equipocatorce.model.DogAppointment
import com.example.equipocatorce.viewmodel.DogAppointmentViewModel
import kotlinx.coroutines.launch

// Fragmento encargado de mostrar y editar los datos de una cita previamente registrada.
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
        getData()
        observers()
        controladores()
        setupEditButton()
        setupToolbar()
    }

    // Obtiene los datos de la cita enviada como argumento
    private fun getData() {
        val args = arguments
        receivedAppointment = args?.getSerializable("dataAppointment") as DogAppointment

        binding.tiNombreMascota.editText?.setText(receivedAppointment.dogName)
        binding.tiNombrePropietario.editText?.setText(receivedAppointment.ownerName)
        binding.tiTelefono.editText?.setText(receivedAppointment.phone)
        binding.tiRaza.editText?.setText(receivedAppointment.dogBreed)

        binding.btnEditarCita.isEnabled = false

        dogAppointmentViewModel.getBreeds()
    }

    // Observa la lista de razas de perros obtenida desde la API y la carga en el AutoCompleteTextView.
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

    // Configura el botón de retroceso en la barra superior.
    private fun setupToolbar() {
        binding.includeToolbar.ivBack.setOnClickListener() {
            findNavController().navigateUp()
        }
    }

    // Controla los campos de texto para habilitar el botón solo si todos están completos.
    private fun controladores() {
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

    }

    // Lógica del botón "Editar cita". Valida los campos, actualiza la imagen según la raza
    private fun setupEditButton() {
        binding.btnEditarCita.setOnClickListener {
            val name = binding.tiNombreMascota.editText?.text.toString().trim()
            val breedInput = binding.tiRaza.editText?.text.toString().trim()
            val owner = binding.tiNombrePropietario.editText?.text.toString().trim()
            val phone = binding.tiTelefono.editText?.text.toString().trim()

            val breed = breedInput.lowercase().replace(" ", "/")

            lifecycleScope.launch {
                try {
                    // Solicita nueva imagen desde API usando la nueva raza
                    val imageResponse = com.example.equipocatorce.webservice.ApiUtils.getApiService().getImage(breed)
                    val updatedAppointment = DogAppointment(
                        id = receivedAppointment.id,
                        dogName = name,
                        dogBreed = breedInput,
                        ownerName = owner,
                        phone = phone,
                        dogSymptom = receivedAppointment.dogSymptom,
                        dogImage = imageResponse.message
                    )
                    dogAppointmentViewModel.updateAppointment(updatedAppointment)
                    Toast.makeText(requireContext(), "Cita actualizada", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_editAppointmentFragment_to_homeFragment)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error al obtener la imagen de la raza", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
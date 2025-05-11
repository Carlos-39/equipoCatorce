package com.example.equipocatorce.view.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.equipocatorce.R
import com.example.equipocatorce.databinding.FragmentNuevaCitaBinding
import com.example.equipocatorce.webservice.ApiUtils
import kotlinx.coroutines.launch
import com.example.equipocatorce.data.DogAppointmentsDB
import com.example.equipocatorce.model.DogAppointment

class NuevaCitaFragment : Fragment(R.layout.fragment_nueva_cita) {

    private var _binding: FragmentNuevaCitaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNuevaCitaBinding.bind(view)

        binding.toolbarNuevaCita.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        controllers()

        val sintomasList = listOf(
            "Síntomas",
            "Solo duerme",
            "No come",
            "Fractura extremidad",
            "Tiene pulgas",
            "Tiene garrapatas",
            "Bota demasiado pelo"
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sintomasList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSintomas.adapter = adapter

        // Cargar razas usando corrutinas
        lifecycleScope.launch {
            try {
                val response = ApiUtils.getApiService().getBreeds()
                val breedsMap = response.message
                val breedList = breedsMap.keys.toList().sorted()
                val breedAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breedList)
                binding.inputRaza.setAdapter(breedAdapter)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al cargar las razas", Toast.LENGTH_SHORT).show()
            }
        }

        // Añadir el mismo TextWatcher a todos los campos
        binding.editTextNombreMascota.addTextChangedListener(textWatcher)
        binding.inputRaza.addTextChangedListener(textWatcher)
        binding.editTextNombrePropietario.addTextChangedListener(textWatcher)
        binding.editTextTelefono.addTextChangedListener(textWatcher)

        validarCampos() // Validación inicial

        // Validar al presionar el botón
        binding.btnGuardarCita.setOnClickListener {
            val sintomaSeleccionado = binding.spinnerSintomas.selectedItem.toString()
            val camposValidos = binding.btnGuardarCita.isEnabled

            if (sintomaSeleccionado == "Síntomas") {
                Toast.makeText(requireContext(), "Selecciona un síntoma", Toast.LENGTH_SHORT).show()
            } else if (camposValidos) {
                val raza = binding.inputRaza.text.toString().lowercase().trim().replace(" ", "/")

                lifecycleScope.launch {
                    try {
                        val imageResponse = ApiUtils.getApiService().getImage(raza)
                        val imageUrl = imageResponse.message

                        val cita = DogAppointment(
                            dogName = binding.editTextNombreMascota.text.toString(),
                            dogBreed = binding.inputRaza.text.toString(),
                            ownerName = binding.editTextNombrePropietario.text.toString(),
                            phone = binding.editTextTelefono.text.toString(),
                            dogSymptom = sintomaSeleccionado,
                            dogImage = imageUrl
                        )

                        val db = DogAppointmentsDB.getDatabase(requireContext())
                        db.dogAppointmentsDao().saveAppointment(cita)
                        Toast.makeText(requireContext(), "Cita creada", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_nuevaCitaFragment_to_homeFragment)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error al obtener la imagen de la raza", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun controllers() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_nuevaCitaFragment_to_homeFragment)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            validarCampos()
        }
    }

    private fun validarCampos() {
        val nombreMascota = binding.editTextNombreMascota.text?.isNotEmpty() == true
        val raza = binding.inputRaza.text?.isNotEmpty() == true
        val propietario = binding.editTextNombrePropietario.text?.isNotEmpty() == true
        val telefono = binding.editTextTelefono.text?.isNotEmpty() == true

        val camposValidos = nombreMascota && raza && propietario && telefono
        binding.btnGuardarCita.isEnabled = camposValidos

        binding.btnGuardarCita.setTextColor(
            resources.getColor(
                if (camposValidos) android.R.color.white else android.R.color.darker_gray,
                null
            )
        )
        binding.btnGuardarCita.setTypeface(null, if (camposValidos) Typeface.BOLD else Typeface.NORMAL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

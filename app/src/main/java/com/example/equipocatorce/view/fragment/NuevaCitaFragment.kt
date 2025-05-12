package com.example.equipocatorce.view.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.equipocatorce.R
import com.example.equipocatorce.databinding.FragmentNuevaCitaBinding
import com.example.equipocatorce.model.DogAppointment
import com.example.equipocatorce.webservice.ApiUtils
import com.example.equipocatorce.data.DogAppointmentsDB
import kotlinx.coroutines.launch

class NuevaCitaFragment : Fragment() {

    // ViewBinding para acceder a las vistas del layout
    private lateinit var binding: FragmentNuevaCitaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNuevaCitaBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    // Se ejecuta después de que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializamos todos los componentes
        setupToolbar()
        setupSymptomsSpinner()
        setupBreedsAutoComplete()
        setupTextWatchers()
        validarCampos()
        setupSaveButton()
    }

    // Configura el botón de regreso en la barra superior
    private fun setupToolbar() {
        binding.toolbarNuevaCita.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    // Llena el Spinner con la lista de síntomas
    private fun setupSymptomsSpinner() {
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
    }

    // Carga las razas desde la API y las muestra en el AutoCompleteTextView
    private fun setupBreedsAutoComplete() {
        lifecycleScope.launch {
            try {
                val response = ApiUtils.getApiService().getBreeds()
                val breedsMap = response.message
                val breedList = breedsMap.keys.toList().sorted()
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breedList)
                binding.inputRaza.setAdapter(adapter)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al cargar las razas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupTextWatchers() {
        val watcher = {
            validarCampos()
        }

        binding.editTextNombreMascota.addTextChangedListener { watcher() }
        binding.inputRaza.addTextChangedListener { watcher() }
        binding.editTextNombrePropietario.addTextChangedListener { watcher() }
        binding.editTextTelefono.addTextChangedListener { watcher() }
    }

    // Valida que todos los campos estén llenos antes de habilitar el botón de guardar
    private fun validarCampos() {
        val nombreMascota = binding.editTextNombreMascota.text?.isNotEmpty() == true
        val raza = binding.inputRaza.text?.isNotEmpty() == true
        val propietario = binding.editTextNombrePropietario.text?.isNotEmpty() == true
        val telefono = binding.editTextTelefono.text?.isNotEmpty() == true

        val camposValidos = nombreMascota && raza && propietario && telefono
        binding.btnGuardarCita.isEnabled = camposValidos

        // Cambia color y tipo de texto del botón según la validez
        binding.btnGuardarCita.setTextColor(
            resources.getColor(
                if (camposValidos) android.R.color.white else android.R.color.darker_gray,
                null
            )
        )
        binding.btnGuardarCita.setTypeface(null, if (camposValidos) Typeface.BOLD else Typeface.NORMAL)
    }

    private fun setupSaveButton() {
        binding.btnGuardarCita.setOnClickListener {
            val sintomaSeleccionado = binding.spinnerSintomas.selectedItem.toString()

            // Verifica que se haya seleccionado un síntoma válido
            if (sintomaSeleccionado == "Síntomas") {
                Toast.makeText(requireContext(), "Selecciona un síntoma", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Prepara la raza en formato esperado por la API
            val raza = binding.inputRaza.text.toString().trim().lowercase().replace(" ", "/")

            // Llama a la API para obtener la imagen de la raza y guarda la cita en la base de datos
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

                    // Guarda la cita en la base de datos local
                    val db = DogAppointmentsDB.getDatabase(requireContext())
                    db.dogAppointmentsDao().saveAppointment(cita)

                    // Muestra confirmación y navega de regreso al home
                    Toast.makeText(requireContext(), "Cita creada", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_nuevaCitaFragment_to_homeFragment)

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error al obtener la imagen de la raza", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

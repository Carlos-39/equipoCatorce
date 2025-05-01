package com.example.equipocatorce.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.equipocatorce.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class NuevaCitaFragment : Fragment() {

    private lateinit var etNombreMascota: TextInputEditText
    private lateinit var autoCompleteRaza: MaterialAutoCompleteTextView
    private lateinit var etNombrePropietario: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var spinnerSintomas: Spinner
    private lateinit var btnGuardarCita: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nueva_cita, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Vinculación de vistas
        etNombreMascota = view.findViewById(R.id.etNombreMascota)
        autoCompleteRaza = view.findViewById(R.id.autoCompleteRaza)
        etNombrePropietario = view.findViewById(R.id.etNombrePropietario)
        etTelefono = view.findViewById(R.id.etTelefono)
        spinnerSintomas = view.findViewById(R.id.spinnerSintomas)
        btnGuardarCita = view.findViewById(R.id.btnGuardarCita)

        // Llenar AutoComplete Raza
        val razas = listOf("Labrador", "Poodle", "Golden Retriever", "Bulldog", "Beagle")
        val razaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, razas)
        autoCompleteRaza.setAdapter(razaAdapter)

        // Llenar Spinner Síntomas
        val sintomas = listOf("Dolor abdominal", "Tos", "Fiebre", "Letargo", "Vómito")
        val sintomasAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sintomas)
        sintomasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSintomas.adapter = sintomasAdapter

        // Opcional: Activar botón solo si todo está lleno
        habilitarBotonSiCamposLlenos()
    }

    private fun habilitarBotonSiCamposLlenos() {
        val campos = listOf(etNombreMascota, autoCompleteRaza, etNombrePropietario, etTelefono)

        val listener = {
            val todoLleno = campos.all { it.text?.isNotBlank() == true }
            btnGuardarCita.isEnabled = todoLleno
        }

        campos.forEach { campo ->
            campo.addTextChangedListener { listener() }
        }
    }
}
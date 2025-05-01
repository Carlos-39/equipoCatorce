package com.example.equipocatorce.view.nuevacita

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.equipocatorce.R
import com.example.equipocatorce.databinding.FragmentNuevaCitaBinding
import com.example.equipocatorce.network.RetrofitClient
import com.example.equipocatorce.network.DogBreedsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevaCitaFragment : Fragment(R.layout.fragment_nueva_cita) {

    private var _binding: FragmentNuevaCitaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNuevaCitaBinding.bind(view)
        binding.toolbarNuevaCita.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
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

        RetrofitClient.dogApiService.getAllBreeds().enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(call: Call<DogBreedsResponse>, response: Response<DogBreedsResponse>) {
                if (response.isSuccessful) {
                    val breedsMap = response.body()?.message
                    val breedList = breedsMap?.keys?.toList()?.sorted() ?: emptyList()

                    val breedAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breedList)
                    binding.inputRaza.setAdapter(breedAdapter)
                }
            }

            override fun onFailure(call: Call<DogBreedsResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error al cargar las razas", Toast.LENGTH_SHORT).show()
                        }
        })

        // Aquí implementaremos toda la lógica más adelante
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
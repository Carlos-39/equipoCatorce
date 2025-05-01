package com.example.equipocatorce.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.equipocatorce.R
import com.example.equipocatorce.databinding.FragmentHomeAdministradorBinding

class HomeAdministradorFragment : Fragment(R.layout.fragment_home_administrador) {

    private var _binding: FragmentHomeAdministradorBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeAdministradorBinding.bind(view)

        // Aquí va tu botón para navegar a NuevaCitaFragment
        binding.miBotonIrNuevaCita.setOnClickListener {
            findNavController().navigate(R.id.action_homeAdministradorFragment_to_nuevaCitaFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

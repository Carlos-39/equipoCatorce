package com.example.equipocatorce.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.equipocatorce.databinding.FragmentDetailsAppointmentBinding


class DetailsAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentDetailsAppointmentBinding

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
        controllers()
    }

    private fun controllers(){

        binding.ivBack.setOnClickListener{
            Toast.makeText(requireContext(), "Regresar a lista de Citas", Toast.LENGTH_SHORT).show()
        }

        binding.fabEdit.setOnClickListener{
            Toast.makeText(requireContext(), "Editar Cita", Toast.LENGTH_SHORT).show()
        }

        binding.fabDelete.setOnClickListener{
            Toast.makeText(requireContext(), "Eliminar esta cita", Toast.LENGTH_SHORT).show()
        }
    }

}
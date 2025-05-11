package com.example.equipocatorce.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.equipocatorce.databinding.FragmentHomePruebaBinding

class HomePrueba : Fragment() {

    private lateinit var binding: FragmentHomePruebaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePruebaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Aquí puedes añadir lógica si necesitas controlar eventos, etc.
        binding.root.setOnClickListener {
            // Ejemplo: mostrar un mensaje al hacer clic en cualquier parte
            // Toast.makeText(context, "Bienvenido a Home", Toast.LENGTH_SHORT).show()
        }
    }
}

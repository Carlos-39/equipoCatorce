package com.example.equipocatorce.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.equipocatorce.databinding.FragmentHomeBinding
import com.example.equipocatorce.view.adapter.DogAppointmentAdapter
import com.example.equipocatorce.viewmodel.DogAppointmentViewModel
import com.example.equipocatorce.R

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    // Se obtiene una instancia del ViewModel, que gestiona la lógica de las citas de los perros
    private val dogAppointmentViewModel: DogAppointmentViewModel by viewModels()

    // Este metodo se llama para crear la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    // Este metodo se llama después de que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Llamamos a los controladores de los eventos de la UI
        controllers()
        // Obtenemos las citas y las mostramos en la UI
        observerAppointments()
    }

    // metodo para manejar las interacciones con la UI
    private fun controllers() {
        binding.addButton.setOnClickListener{
            // Al hacer clic, navegamos hacia la ventana Nueva Cita
            findNavController().navigate(R.id.action_homeFragment_to_nuevaCitaFragment)
        }
    }
    // Este mtodo se llama cuando el fragmento vuelve al primer plano
    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Cuando el usuario presiona el retroceso, cerramos la actividad
            requireActivity().finish()
        }
    }
    // Este metodo observa los datos de las citas y actualiza la UI cuando cambian
    private fun observerAppointments() {
        // Llamamos al metodo del ViewModel para obtener las citas
        dogAppointmentViewModel.getAppointments()
        dogAppointmentViewModel.dogAppointments.observe(viewLifecycleOwner){dogAppointments ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = DogAppointmentAdapter(dogAppointments, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}
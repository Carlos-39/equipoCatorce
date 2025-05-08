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
    private val dogAppointmentViewModel: DogAppointmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllers()
        observerAppointments()
    }

    private fun controllers() {
        binding.addButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_detailAppointment)
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun observerAppointments() {
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
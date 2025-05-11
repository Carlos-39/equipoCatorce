package com.example.equipocatorce.view.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.equipocatorce.viewmodel.LoginViewModel
import com.example.equipocatorce.R
import com.example.equipocatorce.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.lavfinger.setOnClickListener {
            startBiometricAuthentication()
        }
    }

    private fun startBiometricAuthentication() {
        loginViewModel.authenticateWithBiometrics(
            this,
            findNavController(),
            R.id.action_loginFragment_to_homeFragment
        )
    }
}

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
    // ViewModel asociado al fragment para manejar la lógica de autenticación
    private val loginViewModel: LoginViewModel by viewModels()

    // Crea la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el layout utilizando ViewBinding
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    // Se llama después de que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners() // Inicializa los listeners de los elementos interactivos
    }
    // Configura los listeners
    private fun setupListeners() {
        // Listener para cuando se de click en el icono animado de huella
        binding.lavfinger.setOnClickListener {
            startBiometricAuthentication() // Inicia el proceso de autenticación biométrica
        }
    }
    // Función que inicia la autenticación biométrica usando el ViewModel
    private fun startBiometricAuthentication() {
        loginViewModel.authenticateWithBiometrics(
            this,                             // Referencia al fragmento actual
            findNavController(),                      // NavController para navegar si la autenticación es exitosa
            R.id.action_loginFragment_to_homeFragment // Acción de navegación a ejecutar
        )
    }
}

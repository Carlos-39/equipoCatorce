// ViewModel que se encarga de gestionar la autenticacion con huella de la app
package com.example.equipocatorce.viewmodel
import android.app.Application
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.AuthenticationCallback
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    //Se obtiene el contexto de la aplicacion
    private val context = getApplication<Application>()

    // Función para autenticar mediante biometria, en este caso huella dactilar
    fun authenticateWithBiometrics(fragment: Fragment, navController: NavController, destination: Int) {
        // Obtiene el BiometricManager para verificar las capacidades biométricas del dispositivo
        val biometricManager = BiometricManager.from(context)
        // Se verifica si el dispositivo puede autenticar con BIOMETRIC_STRONG (Metodos biometricos de alta seguridad (huellas, reconocimiento facial, etc.) )
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BIOMETRIC_ERROR_HW_UNAVAILABLE -> return    // Hardware no disponible temporalmente
            BIOMETRIC_ERROR_NO_HARDWARE -> return       // El dispositivo no tiene hardware biométrico
            BIOMETRIC_ERROR_NONE_ENROLLED -> return     // El usuario no ha registrado datos biométricos
            BIOMETRIC_SUCCESS -> {}                     // Todo correcto, se puede proceder
            else -> return                              // Otro error desconocido
        }

        // Construye la ventana emergente para autenticacion
        val promptInfo = PromptInfo.Builder()
            .setTitle("Autenticación con Biometría") // Titulo de la ventana emergente
            .setDescription("Ingrese su huella digital") // Subtitulo de la ventana emergente
            .setAllowedAuthenticators(BIOMETRIC_STRONG) // Tipo de autenticacion permitida
            .setNegativeButtonText("Cancelar") // Boton de cancelar
            .build()

        val executor = ContextCompat.getMainExecutor(context)

        // Crea el prompt biométrico con sus callback (función que se ejecuta automáticamente en respuesta a un evento) para manejar los resultados
        val biometricPrompt = BiometricPrompt(fragment, executor,
            object : AuthenticationCallback() {
                // Se ejecuta si ocurre un error durante la autenticación
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }
                // Se ejecuta si la autenticación fue exitosa
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    navController.navigate(destination) // Navega a la pantalla destino
                }
                // Se ejecuta si la autenticación falla (huella no reconocida)
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })
        // Aqui se lanza la ventana emergente
        biometricPrompt.authenticate(promptInfo)
    }
}

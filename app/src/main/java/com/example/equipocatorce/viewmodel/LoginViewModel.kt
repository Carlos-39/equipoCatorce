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

    private val context = getApplication<Application>()

    fun authenticateWithBiometrics(fragment: Fragment, navController: NavController, destination: Int) {
        val biometricManager = BiometricManager.from(context)

        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BIOMETRIC_ERROR_HW_UNAVAILABLE -> return
            BIOMETRIC_ERROR_NO_HARDWARE -> return
            BIOMETRIC_ERROR_NONE_ENROLLED -> return
            BIOMETRIC_SUCCESS -> {} // ready to authenticate
            else -> return
        }

        val promptInfo = PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setDescription("Place your finger on the sensor")
            .setAllowedAuthenticators(BIOMETRIC_STRONG)
            .setNegativeButtonText("Cancel")
            .build()

        val executor = ContextCompat.getMainExecutor(context)

        val biometricPrompt = BiometricPrompt(fragment, executor,
            object : AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    navController.navigate(destination)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })

        biometricPrompt.authenticate(promptInfo)
    }
}

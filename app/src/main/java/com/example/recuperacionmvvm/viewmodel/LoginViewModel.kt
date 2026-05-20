package com.example.recuperacionmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorDialog = MutableStateFlow<String?>(null)
    val errorDialog: StateFlow<String?> = _errorDialog.asStateFlow()

    fun login(email: String, psw: String, onSuccess: () -> Unit) {
        if (email.isBlank() || psw.isBlank()) {
            _errorDialog.value = "Por favor, rellena todos los campos"
            return
        }

        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, psw)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    Log.d("LoginViewModel", "Login exitoso")
                    onSuccess()
                } else {
                    Log.e("LoginViewModel", "Error en el login", task.exception)
                    _errorDialog.value = "Usuario o contraseña incorrectos"
                }
            }
    }

    fun dismissError() {
        _errorDialog.value = null
    }
}

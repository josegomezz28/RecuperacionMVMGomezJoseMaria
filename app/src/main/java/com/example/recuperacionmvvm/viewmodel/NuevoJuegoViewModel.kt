package com.example.recuperacionmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.recuperacionmvvm.model.Juego
import com.google.firebase.firestore.FirebaseFirestore

class NuevoJuegoViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    fun addJuego(juego: Juego, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        db.collection("Juegos")
            .add(juego)
            .addOnSuccessListener {
                Log.d("NuevoJuegoViewModel", "Juego añadido con éxito")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("NuevoJuegoViewModel", "Error al añadir juego", e)
                onError(e)
            }
    }
}

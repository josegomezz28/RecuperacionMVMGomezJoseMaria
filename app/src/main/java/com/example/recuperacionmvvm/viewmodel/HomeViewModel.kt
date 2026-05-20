package com.example.recuperacionmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.recuperacionmvvm.model.Juego
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _juegos = MutableStateFlow<List<Juego>>(emptyList())
    val juegos: StateFlow<List<Juego>> = _juegos.asStateFlow()

    init {
        getJuegos()
    }

    private fun getJuegos() {
        db.collection("Juegos")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("HomeViewModel", "Error.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Juego::class.java)?.apply { id = doc.id }
                    }
                    _juegos.value = list
                }
            }
    }

    fun eliminarJuego(id: String) {
        db.collection("Juegos").document(id)
            .delete()
            .addOnSuccessListener {
                Log.d("HomeViewModel", "El juego ha sido eliminado correctamente: $id")
            }
            .addOnFailureListener { e ->
                Log.e("HomeViewModel", "Error al eliminar juego: $id", e)
            }
    }
}

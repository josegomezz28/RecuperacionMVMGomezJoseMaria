package com.example.recuperacionmvvm.model

import com.google.firebase.firestore.Exclude

data class Juego(
    @get:Exclude var id: String? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val plataforma: String = "",
    val nota: Double = 0.0,
    val imagen: String = ""
)

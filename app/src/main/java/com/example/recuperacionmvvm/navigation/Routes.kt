package com.example.recuperacionmvvm.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes : NavKey {
    @Serializable
    data object Login : Routes()

    @Serializable
    data object Home : Routes()

    @Serializable
    data object NuevoJuego : Routes()
}

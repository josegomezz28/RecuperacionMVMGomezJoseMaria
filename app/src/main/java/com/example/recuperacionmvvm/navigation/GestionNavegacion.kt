package com.example.recuperacionmvvm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.recuperacionmvvm.ui.screens.HomeScreen
import com.example.recuperacionmvvm.ui.screens.LoginScreen
import com.example.recuperacionmvvm.ui.screens.NuevoJuegoScreen

@Composable
fun GestionNavegacion() {
    val backStack = rememberNavBackStack(Routes.Login)

    NavDisplay(
        backStack = backStack,
        onBack = { if (backStack.size > 1) backStack.removeAt(backStack.size - 1) },
        entryProvider = entryProvider {
            entry<Routes.Login> {
                LoginScreen(
                    onLoginSuccess = {
                        backStack.add(Routes.Home)
                    }
                )
            }
            entry<Routes.Home> {
                HomeScreen(
                    onNavigateToNuevoJuego = {
                        backStack.add(Routes.NuevoJuego)
                    }
                )
            }
            entry<Routes.NuevoJuego> {
                NuevoJuegoScreen(
                    onBack = {
                        if (backStack.size > 1) {
                            backStack.removeAt(backStack.size - 1)
                        }
                    }
                )
            }
        }
    )
}

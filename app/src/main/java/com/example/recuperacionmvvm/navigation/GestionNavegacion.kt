package com.example.recuperacionmvvm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.recuperacionmvvm.ui.screens.LoginScreen

@Composable
fun GestionNavegacion() {
    val backStack = rememberNavBackStack(initialKey = Routes.Login)

    NavDisplay(
        backstack = backStack,
        entryProvider = { key ->
            when (key) {
                is Routes.Login -> {
                    LoginScreen(
                        onLoginSuccess = {
                            backStack.push(Routes.Home)
                        }
                    )
                }
                is Routes.Home -> {

                }
                is Routes.NuevoJuego -> {

                }
            }
        }
    )
}

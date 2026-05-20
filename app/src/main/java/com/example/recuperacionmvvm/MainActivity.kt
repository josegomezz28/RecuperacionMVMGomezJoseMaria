package com.example.recuperacionmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.recuperacionmvvm.navigation.GestionNavegacion
import com.example.recuperacionmvvm.ui.theme.RecuperacionMVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecuperacionMVVMTheme {
                GestionNavegacion()
            }
        }
    }
}

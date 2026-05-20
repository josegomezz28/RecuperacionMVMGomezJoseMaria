package com.example.recuperacionmvvm.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recuperacionmvvm.model.Juego
import com.example.recuperacionmvvm.ui.theme.BotonLoginColor
import com.example.recuperacionmvvm.viewmodel.NuevoJuegoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoJuegoScreen(
    onBack: () -> Unit,
    viewModel: NuevoJuegoViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var plataforma by remember { mutableStateOf("PC") }
    var nota by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val plataformas = listOf("PC", "PS5", "Xbox", "Nintendo Switch")
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Juego", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = plataforma,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Plataforma") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    plataformas.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                plataforma = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = nota,
                onValueChange = { if (it.isEmpty() || it.toDoubleOrNull() != null) nota = it },
                label = { Text("Nota (0-10)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                value = imagen,
                onValueChange = { imagen = it },
                label = { Text("URL de la imagen") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && nota.toDoubleOrNull() != null) {
                        val juego = Juego(
                            nombre = nombre,
                            descripcion = descripcion,
                            plataforma = plataforma,
                            nota = nota.toDouble(),
                            imagen = imagen
                        )
                        viewModel.addJuego(
                            juego = juego,
                            onSuccess = {
                                Toast.makeText(context, "Juego añadido", Toast.LENGTH_SHORT).show()
                                onBack()
                            },
                            onError = {
                                Toast.makeText(context, "Error al añadir", Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, "Rellena los campos obligatorios", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BotonLoginColor)
            ) {
                Text("Añadir Juego", color = Color.White)
            }
        }
    }
}

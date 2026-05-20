package com.example.recuperacionmvvm.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.recuperacionmvvm.R
import com.example.recuperacionmvvm.model.Juego
import com.example.recuperacionmvvm.ui.theme.BotonLoginColor
import com.example.recuperacionmvvm.ui.theme.FondoCardExamen
import com.example.recuperacionmvvm.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToNuevoJuego: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val juegos by viewModel.juegos.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Videojuegos", fontWeight = FontWeight.Bold) }
            )
        },
        floatingActionButton = {
            Button(
                onClick = onNavigateToNuevoJuego,
                colors = ButtonDefaults.buttonColors(containerColor = BotonLoginColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp)
                    .height(48.dp)
            ) {
                Text("Agregar Videojuego", color = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(juegos, key = { it.id ?: it.hashCode() }) { juego ->
                JuegoCard(
                    juego = juego,
                    onDelete = { juego.id?.let { viewModel.eliminarJuego(it) } }
                )
            }
        }
    }
}

@Composable
fun JuegoCard(juego: Juego, onDelete: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = FondoCardExamen),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp, 40.dp)
                        .background(
                            color = when {
                                juego.nota < 5 -> Color.Red
                                juego.nota < 7 -> Color(0xFFFFA500)
                                else -> Color(0xFF27D21F)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = juego.nota.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                AsyncImage(
                    model = juego.imagen,
                    contentDescription = juego.nombre,
                    modifier = Modifier
                        .height(80.dp)
                        .weight(1f),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.logo_app),
                    error = painterResource(R.drawable.logo_app)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = juego.nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    val iconRes = when (juego.plataforma.lowercase()) {
                        "nintendo switch" -> R.drawable.nintendo
                        "ps5" -> R.drawable.ps
                        "pc" -> R.drawable.windows
                        "xbox" -> R.drawable.xbox
                        else -> null
                    }

                    iconRes?.let {
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = R.drawable.imagenjuego.toString(),
                            modifier = Modifier.size(24.dp).padding(vertical = 2.dp)
                        )
                    }
                }

                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
            Text(
                text = juego.descripcion,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

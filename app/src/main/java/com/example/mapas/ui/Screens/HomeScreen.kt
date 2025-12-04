package com.example.mapas.ui.Screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mapas.ui.ViewModels.HomeViewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToCamera: () -> Unit,
    onNavigateToMap: () -> Unit
) {
    // Observar el mensaje de bienvenida desde el ViewModel
    val welcomeMessage by viewModel.mensajeBienVenida.observeAsState("¡Bienvenido!")

    // Animación de rotación infinita
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de bienvenida
        Text(
            text = welcomeMessage,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Icono animado con rotación
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Icono animado",
            modifier = Modifier
                .size(120.dp)
                .rotate(rotation),
            tint = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Botón "Ir a Cámara"
        Button(
            onClick = onNavigateToCamera,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Cámara"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Ir a Cámara")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Ir a Mapa"
        OutlinedButton(
            onClick = onNavigateToMap,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = "Mapa"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Ir a Mapa")
        }
    }
}
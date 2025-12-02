package com.example.mapas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mapas.ui.theme.MapasTheme
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración inicial de OSMDroid
        // Esto solo se hace una vez al iniciar la app
        Configuration.getInstance().userAgentValue = packageName

        setContent {
            MapasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaMapaBasico()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapaBasico() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Mapa") }
            )
        }
    ) { padding ->
        // AndroidView nos permite usar vistas de Android tradicionales en Compose
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            factory = { ctx ->
                // === PASO 1: Crear el MapView ===
                // MapView es el componente que muestra el mapa
                val mapView = MapView(ctx)

                // === PASO 2: Configurar el mapa ===
                mapView.setTileSource(TileSourceFactory.MAPNIK) // Estilo de mapa
                        mapView.setMultiTouchControls(true) // Permitir zoom con dos dedos

                // === PASO 3: Definir la ubicación inicial ===
                // GeoPoint = punto geográfico (latitud, longitud)
                // Ejemplo: Madrid, España
                val ubicacionMadrid = GeoPoint(40.4168, -3.7038)

                // === PASO 4: Configurar la cámara ===
                val controller = mapView.controller
                controller.setZoom(12.0) // Nivel de zoom (1-20)
                controller.setCenter(ubicacionMadrid) // Centrar en Madrid

                // === PASO 5: Añadir un marcador ===
                val marcador = Marker(mapView)
                marcador.position = ubicacionMadrid
                marcador.title = "Madrid"
                marcador.snippet = "Capital de España"
                marcador.setAnchor(Marker.ANCHOR_CENTER,
                    Marker.ANCHOR_BOTTOM)

                // Añadir el marcador al mapa
                mapView.overlays.add(marcador)

                // Devolver el mapa configurado
                mapView
            }
        )
    }
}

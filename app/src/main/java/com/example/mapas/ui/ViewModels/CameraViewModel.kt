package com.example.mapas.ui.ViewModels

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class CameraViewModel : ViewModel() {

    // LiveData que contiene la lista de URIs de las fotos guardadas
    private val _photos = MutableLiveData<List<Uri>>(emptyList())
    val photos: LiveData<List<Uri>> = _photos

    /**
     * Carga todas las fotos existentes desde la carpeta específica de la aplicación.
     * Lee los archivos de imagen desde el directorio de fotos de la app.
     */
    fun loadPhotos(context: Context) {
        val photosDir = File(context.filesDir, "photos")

        if (!photosDir.exists()) {
            photosDir.mkdirs()
            _photos.value = emptyList()
            return
        }

        val photoFiles = photosDir.listFiles { file ->
            file.isFile && (file.extension.equals("jpg", ignoreCase = true) ||
                    file.extension.equals("jpeg", ignoreCase = true) ||
                    file.extension.equals("png", ignoreCase = true))
        }

        val uris = photoFiles?.map { file ->
            Uri.fromFile(file)
        }?.sortedByDescending { uri ->
            File(uri.path!!).lastModified()
        } ?: emptyList()

        _photos.value = uris
    }

    /**
     * Añade una nueva foto a la lista cuando el usuario captura una imagen.
     * Actualiza el LiveData para que la interfaz se refresque automáticamente.
     */
    fun addPhoto(uri: Uri) {
        val currentPhotos = _photos.value?.toMutableList() ?: mutableListOf()
        // Añadir la nueva foto al principio de la lista
        currentPhotos.add(0, uri)
        _photos.value = currentPhotos
    }

    /**
     * Crea un archivo temporal para guardar la foto capturada y devuelve su URI.
     * Útil para usar con la cámara del sistema.
     */
    fun createPhotoUri(context: Context): Uri {
        val photosDir = File(context.filesDir, "photos")
        if (!photosDir.exists()) {
            photosDir.mkdirs()
        }

        val photoFile = File(photosDir, "photo_${System.currentTimeMillis()}.jpg")

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            photoFile
        )
    }

    /**
     * Obtiene el número total de fotos almacenadas.
     */
    fun getPhotoCount(): Int {
        return _photos.value?.size ?: 0
    }
}

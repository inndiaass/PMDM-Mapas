package com.example.mapas.ui.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeViewModel {


    private val _mensajeBienVenida = MutableLiveData<String>()
    val mensajeBienVenida: LiveData<String> = _mensajeBienVenida



}
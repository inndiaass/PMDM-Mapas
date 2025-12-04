package com.example.mapas.ui.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {


    private val _mensajeBienVenida = MutableLiveData<String>()
    val mensajeBienVenida: LiveData<String> = _mensajeBienVenida



}
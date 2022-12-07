package com.example.projetbar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetbar.Bar


class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Liste des Bars :"
    }
    val text: LiveData<String> = _text

    var listBar = mutableListOf(Bar("Le BoBar",5,2), Bar("Le Tonneau Sans Fond",5,2), Bar("Le BarbeRousse",5,2))


}
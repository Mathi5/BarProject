package com.example.projetbar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    var num = 0
    private val _text = MutableLiveData<String>().apply {
        value = num.toString()
    }
    val text: LiveData<String> = _text
}
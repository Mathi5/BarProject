package com.example.projetbar.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetbar.Bar

class DetailFragementViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    public lateinit var selectedBar: Bar



    fun getSelectedBar(bar: Bar) {
        selectedBar = bar

        val _text = MutableLiveData<String>().apply {
            value = bar.name
        }
        val text: LiveData<String> = _text
    }
}
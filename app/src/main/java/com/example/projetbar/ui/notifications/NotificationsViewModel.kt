package com.example.projetbar.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Tends ton bras, lève une jambe et tiens ton portable le plus droit possible"
    }
    val text: LiveData<String> = _text
}
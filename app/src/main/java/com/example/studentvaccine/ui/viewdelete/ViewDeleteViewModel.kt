package com.example.studentvaccine.ui.viewdelete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewDeleteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is viewdelete Fragment"
    }
    val text: LiveData<String> = _text
}
package com.example.studentvaccine.ui.addstudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddStudentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is addstudent Fragment"
    }
    val text: LiveData<String> = _text
}
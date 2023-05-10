package com.example.vk

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vk.db.DBViewModel

class MyViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DBViewModel::class.java)) {
            return DBViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
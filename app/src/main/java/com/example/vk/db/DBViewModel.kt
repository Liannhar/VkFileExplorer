package com.example.vk.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DBViewModel(application: Application) : AndroidViewModel(application)  {
    val fileRepository : FileRepository

    init {
        val dao = FileDatabase.getDatabase(application).getFileDao()
        fileRepository = FileRepository(dao)
    }

    fun deleteFile (file: File) = viewModelScope.launch(Dispatchers.IO) {
        fileRepository.delete(file)
    }

    fun updateFile(file: File) = viewModelScope.launch(Dispatchers.IO) {
        fileRepository.update(file)
    }


    fun addFile(file: File) = viewModelScope.launch(Dispatchers.IO) {
        fileRepository.insert(file)
    }


}
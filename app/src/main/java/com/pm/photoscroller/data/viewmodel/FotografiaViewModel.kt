package com.pm.photoscroller.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pm.photoscroller.data.database.FotografiaDatabase
import com.pm.photoscroller.data.entities.Fotografia
import com.pm.photoscroller.data.repository.FotografiaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FotografiaViewModel(application: Application) : AndroidViewModel(application) {
    val readAllFotografia: LiveData<List<Fotografia>>
    private val repository : FotografiaRepository

    init {
        val fotografiaDAO = FotografiaDatabase.getDatabase(application).fotografiaDAO()
        repository = FotografiaRepository(fotografiaDAO)
        readAllFotografia = repository.readAllFotografia
    }

    fun  addFotografia(fotografia: Fotografia){
        viewModelScope.launch(Dispatchers.IO) { repository.addFotografia(fotografia) }
    }

    fun updateFotografia(fotografia: Fotografia){
        viewModelScope.launch(Dispatchers.IO) { repository.updateFotografia(fotografia) }
    }

    fun deleteFotografia(fotografia: Fotografia){
        viewModelScope.launch(Dispatchers.IO) { repository.deleteFotografia(fotografia) }
    }
}
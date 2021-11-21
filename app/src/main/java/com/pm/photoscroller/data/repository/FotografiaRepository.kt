package com.pm.photoscroller.data.repository

import androidx.lifecycle.LiveData
import com.pm.photoscroller.data.dao.FotografiaDAO
import com.pm.photoscroller.data.entities.Fotografia

class FotografiaRepository(private val fotografiaDAO: FotografiaDAO) {
    val readAllFotografia : LiveData<List<Fotografia>> = fotografiaDAO.readAllFotografias()

    suspend fun addFotografia(fotografia: Fotografia){
        fotografiaDAO.addFotografia(fotografia)
    }
    suspend fun updateFotografia(fotografia: Fotografia){
        fotografiaDAO.updateFotografia(fotografia)
    }
    suspend fun deleteFotografia(fotografia: Fotografia){
        fotografiaDAO.deleteFotografia(fotografia)
    }
}
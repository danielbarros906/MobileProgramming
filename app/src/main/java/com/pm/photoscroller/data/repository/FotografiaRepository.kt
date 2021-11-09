package com.pm.photoscroller.data.repository

import androidx.lifecycle.LiveData
import com.pm.photoscroller.data.dao.FotografiaDao
import com.pm.photoscroller.data.entities.Fotografia

class FotografiaRepository(private val fotografiaDao: FotografiaDao) {
    val readAllFotografia : LiveData<List<Fotografia>> = fotografiaDao.readAllFotografias()

    suspend fun addFotografia( fotografia: Fotografia){
        fotografiaDao.addFotografia(fotografia)
    }

    suspend fun updateFotografia(fotografia: Fotografia){
        fotografiaDao.updateFotografia(fotografia)
    }

    suspend fun deleteFotografia(fotografia: Fotografia){
        fotografiaDao.deleteFotografia(fotografia)
    }
}
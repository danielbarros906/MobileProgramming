package com.pm.photoscroller.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pm.photoscroller.data.entities.Fotografia

@Dao
interface FotografiaDAO{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFotografia(fotografia: Fotografia)

    @Update
    fun updateFotografia(fotografia: Fotografia)

    @Query("SELECT * FROM fotografia ORDER BY id DESC")
    fun readAllFotografias(): LiveData<List<Fotografia>>

    @Delete
    fun deleteFotografia(fotografia: Fotografia)
}
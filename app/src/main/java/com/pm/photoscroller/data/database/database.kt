package com.pm.photoscroller.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pm.photoscroller.data.dao.FotografiaDao
import com.pm.photoscroller.data.entities.Fotografia

@Database(entities = [Fotografia :: class], version = 1, exportSchema = false)

abstract class FotografiaDatabase : RoomDatabase() {

    abstract fun fotografiaDao() : FotografiaDao

    companion object{
        @Volatile
        private var INSTANCE: FotografiaDatabase? = null

        fun getDatabase(context: Context): FotografiaDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FotografiaDatabase::class.java,
                    "fotografia_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
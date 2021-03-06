package com.pm.photoscroller.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pm.photoscroller.data.dao.FotografiaDAO
import com.pm.photoscroller.data.entities.Fotografia

@Database(
    entities = [Fotografia::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [AutoMigration (from = 2, to = 3)])
abstract class FotografiaDatabase : RoomDatabase(){

    abstract fun fotografiaDAO() : FotografiaDAO
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
                    "photoscroller_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
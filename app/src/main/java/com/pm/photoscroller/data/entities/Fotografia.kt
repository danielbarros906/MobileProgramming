package com.pm.photoscroller.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Fotografia")
class Fotografia(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) : Parcelable
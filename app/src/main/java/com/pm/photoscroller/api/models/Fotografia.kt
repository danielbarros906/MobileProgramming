package com.pm.photoscroller.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fotografia (
    val id: Int,
    val title: String,
    val aperture: Float,
    val shutter: Int,
    val iso: Int,
    val description: String,
    val location: String,
    val users_id: Int,
    val upload_date: String,
    val user_name: String,
    val photo_path: String
    ):Parcelable
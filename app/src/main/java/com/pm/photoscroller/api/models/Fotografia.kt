package com.pm.photoscroller.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fotografia (
    val id: Int,
    val title: String,
    val aperture: String,
    val shutter: String,
    val iso: String,
    val description: String,
    val location: String,
    val users_id: Int,
    val created_at: String,
    val user_name: String,
    val photo_path: String
    ):Parcelable
package com.pm.photoscroller.api.dto

import com.pm.photoscroller.api.models.Fotografia

data class FotografiaDto(
    val status: String,
    val message: String,
    val fotografia: Fotografia
)
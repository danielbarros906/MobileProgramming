package com.pm.photoscroller.api.dto

import com.pm.photoscroller.api.models.User

data class UserDto(
    val status: String,
    val message: String,
    val user: List<User>,
    val token: String
)

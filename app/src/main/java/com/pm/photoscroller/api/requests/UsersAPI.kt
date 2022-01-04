package com.pm.photoscroller.api.requests

import com.pm.photoscroller.api.dto.UserDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Call


interface UsersAPI {
    @FormUrlEncoded
    @POST(("users/signin"))
    fun signin(
        @Field("username") username : String,
        @Field("password") password : String
    ):Call<UserDto>
}
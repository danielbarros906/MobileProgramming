package com.pm.photoscroller.api.requests

import com.pm.photoscroller.api.dto.FotografiaDto
import com.pm.photoscroller.api.models.Fotografia
import retrofit2.Call
import retrofit2.http.*

interface FotografiaAPI {
    @GET ("photos/read")
    fun getFotografias(
        @Header("Authorization") token: String
    ) : Call<List<Fotografia>>

    @FormUrlEncoded
    @POST ("photos/create")
    //@Headers("Content-Type: application/json")
    fun createFotografia(
        @Header("Authorization") token: String,
        @Field("users_id") users_id: String?,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("location") location: String,
        @Field("aperture") aperture: String,
        @Field("shutter") shutter: String,
        @Field("iso") iso: String,
        @Field("photo_path") photo_path: String
    ): Call<FotografiaDto>

    @FormUrlEncoded
    @POST ("photos/update")
    fun updateFotografia(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("aperture") aperture: String,
        @Field("shutter") shutter: String,
        @Field("iso") iso: String,
        @Field("location") location: String
    ): Call<FotografiaDto>

    @FormUrlEncoded
    @POST ("photos/delete")
    fun deleteFotografia(
        @Header("Authorization") token: String,
        @Field("id") id: Int
    ): Call<FotografiaDto>
}
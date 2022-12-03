package com.example.assignment.api

import com.example.assignment.models.UserRequest
import com.example.assignment.models.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @POST("api/login")
    suspend fun login(@Body userRequest: UserRequest): Response<UserResponse>

}
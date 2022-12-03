package com.example.assignment.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("auth_token")
    val token: String
)

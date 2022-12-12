package com.example.assignment.api

import com.example.assignment.models.MessageRequest
import com.example.assignment.models.Messages
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface MessageAPI {

    @GET("api/messages")
    suspend fun getMessages(
        @HeaderMap headerMap: HashMap<String, String>
    ): List<Messages>

    @POST("api/messages")
    suspend fun postMessage(@Body messageRequest: MessageRequest,
                            @HeaderMap headerMap: HashMap<String, String>
    ): Response<Messages>
}
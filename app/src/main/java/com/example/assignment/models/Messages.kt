package com.example.assignment.models

data class Messages(
    val id: Int,
    val thread_id: Int,
    val user_id: String,
    val body: String,
    val timestamp: String,
    val agent_id: String
)

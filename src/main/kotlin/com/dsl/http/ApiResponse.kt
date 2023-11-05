package com.dsl.http

data class ApiResponse<T>(
    val code: String,
    val body: T,
)

package com.dsl.http

data class HttpRequest(
    var url: String = "",
    var method: HttpMethod = HttpMethod.GET,
    var headers: Map<String, String> = emptyMap(),
    var retryPolicy: RetryPolicy = RetryPolicy(),
)
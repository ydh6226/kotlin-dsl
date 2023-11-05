package com.dsl.resttemplate

import com.dsl.http.ApiResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

fun main() {
    val request = SignUpRequest("name", "email", "password")

    val restTemplate = RestTemplate()

    val response: ApiResponse<SignUpResponse> = restTemplate.apiCall {
        url("https://api.github.com/members/{id}")
        method(HttpMethod.GET)
        headers = mapOf(
            "header" to "header",
        )
        queryParams(
            "name" to "name",
        )
        requestBody = request
    }
}

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
)

data class SignUpResponse(
    val id: Long,
)

inline fun <reified T> RestTemplate.apiCall(httpRequest: FooHttpRequest.() -> Unit): ApiResponse<T> {
    val request = FooHttpRequest().also { httpRequest(it) }

    val headers = HttpHeaders().also {
        request.headers.forEach { (key, value) ->
            it.add(key, value)
        }
    }

    val uriComponents = UriComponentsBuilder.fromHttpUrl(request.url)
        .uriVariables(request.uriVariables)
        .also {
            request.queryParams.forEach { (key, value) ->
                it.queryParam(key, value)
            }
        }
        .build()

    return this.exchange(
        uriComponents.toUri(),
        request.method,
        HttpEntity(request.requestBody, headers),
        object : ParameterizedTypeReference<ApiResponse<T>>() {}
    ).body ?: throw IllegalStateException("Response body is null")
}


data class FooHttpRequest(
    var method: HttpMethod = HttpMethod.GET,
    var url: String = "",
    var headers: Map<String, String> = mapOf(),
    var uriVariables: Map<String, Any> = mapOf(),
    var queryParams: Map<String, Any> = mapOf(),
    var requestBody: Any? = null,
) {
    fun url(uri: String) {
        this.url = uri
    }

    fun method(method: HttpMethod) {
        this.method = method
    }

    fun queryParams(vararg queryParams: Pair<String, Any>) {
        mutableMapOf<String, Any>().also {
            queryParams.forEach { (key, value) ->
                it[key] = value
            }
        }
    }
}

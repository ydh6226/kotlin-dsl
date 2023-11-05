package com.dsl.http

import kotlin.reflect.full.primaryConstructor

inline fun <reified T: Any> httpRequest(requestBlock: HttpRequest.() -> Unit): T {
    HttpRequest().also { requestBlock(it) }
    return T::class.primaryConstructor?.call() ?: throw IllegalStateException()
}

fun retryPolicy(policyBlock: RetryPolicy.() -> Unit): RetryPolicy {
    return RetryPolicy().also { policyBlock(it) }
}

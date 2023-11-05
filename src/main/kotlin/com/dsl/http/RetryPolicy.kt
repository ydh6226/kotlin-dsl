package com.dsl.http

data class RetryPolicy(
    var target: RetryTarget = RetryTarget.None,
    var count: Int = 0,
)

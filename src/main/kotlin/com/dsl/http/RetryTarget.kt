package com.dsl.http

sealed class RetryTarget {
    object None : RetryTarget()
    object All: RetryTarget()
    class Some(vararg codes: Int) : RetryTarget()
}

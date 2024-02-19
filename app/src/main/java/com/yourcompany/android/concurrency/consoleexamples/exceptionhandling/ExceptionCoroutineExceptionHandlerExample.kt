package com.yourcompany.android.concurrency.consoleexamples.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Creating a big guy who can handle everything.\n")
    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("\nI got you $throwable")
    }
    val launch = GlobalScope.launch(coroutineExceptionHandler) {
        println("Again you, please get serious, again throwing an exception.")

        throw IllegalArgumentException()
    }

    val deferred = GlobalScope.async(coroutineExceptionHandler) {
        println("Just messing with you, this won't be visible but let's throw an exception.")

        throw NullPointerException("This will not happen because other coroutine failes before")
    }
    joinAll(launch, deferred)
}

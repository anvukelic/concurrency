package com.yourcompany.android.concurrency.consoleexamples.exceptionhandling

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val exceptionInCoroutineOneJob = GlobalScope.launch {
        println("Gush, something went wrong throwing an exception in launch coroutine")

        throw IllegalArgumentException("I am handled by the Thread.defaultUncaughtExceptionHandler")
    }

    exceptionInCoroutineOneJob.join()
    println("\nI am done, please go to the next example \"ExceptionInAsyncCoroutine.kt\"")
}

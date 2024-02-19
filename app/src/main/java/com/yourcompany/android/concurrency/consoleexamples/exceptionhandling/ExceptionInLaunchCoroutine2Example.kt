package com.yourcompany.android.concurrency.consoleexamples.exceptionhandling

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val exceptionInCoroutineOneJob = GlobalScope.launch {
        println("Cmon, let's get serious. We'll stop here, I am throwing an exception in launch coroutine.")

        throw IllegalArgumentException("Forget about Thread.defaultUncaughtExceptionHandler, let's handle it together")
    }

    exceptionInCoroutineOneJob.invokeOnCompletion { exception ->
        println("Gotta catch em all!\n$exception")
    }

    exceptionInCoroutineOneJob.join()
}

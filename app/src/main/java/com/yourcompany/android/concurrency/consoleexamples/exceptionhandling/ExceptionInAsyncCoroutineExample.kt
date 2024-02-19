package com.yourcompany.android.concurrency.consoleexamples.exceptionhandling

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val deferred = GlobalScope.async {
        println("Wow you've messed up something reeeeealy bad! Throwing an exception in async coroutine")

        throw NullPointerException("Will happen when only if a user calls await")
    }

    try {
        deferred.await()
        println("You shell not pass!")
    } catch (e: Exception) {
        println("Got you! ${e.javaClass.simpleName}")
    }
    println("\nI am done, please go to the next example \"ExceptionInLaunchCoroutine2.kt\"")
}

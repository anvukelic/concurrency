package com.yourcompany.android.concurrency.consoleexamples.exceptionhandling

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.yield
import java.lang.IllegalArgumentException

fun main() = runBlocking {
    try {
        supervisorScope {
            // Launch the child coroutine
            launch {
                try {
                    println("The child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("The child is cancelled")
                }
            }
            // Give our child a chance to execute and print using yield
            yield()
            println("Throwing an exception from the scope")
            throw IllegalArgumentException()
        }
    } catch (exception: IllegalArgumentException) {
        println("I've caught an $exception")
    }
}

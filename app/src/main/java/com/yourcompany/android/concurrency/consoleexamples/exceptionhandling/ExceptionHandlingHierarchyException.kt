package com.yourcompany.android.concurrency.consoleexamples.exceptionhandling

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val supervisor = SupervisorJob()
    with(CoroutineScope(Dispatchers.IO + supervisor)) {
        val firstCoroutine = launch(CoroutineExceptionHandler { _, _ -> }) {
            println("The first child is failing")
            throw AssertionError("The first child is cancelled")
        }
        val secondCoroutine = launch {
            firstCoroutine.join()
            // Because of the supervisor, firstCoroutine cancellation doesn't affect on secondCoroutine status
            println("The first child is cancelled: ${firstCoroutine.isCancelled}, but the second one is still active")
            try {
                delay(Long.MAX_VALUE)
            } catch (exception: CancellationException) {
                println("The second child is cancelled because the supervisor was cancelled")
            }
        }

        firstCoroutine.join()
        println("Cancelling the supervisor")
        supervisor.cancel()
        secondCoroutine.join()
    }
}

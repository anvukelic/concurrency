package com.yourcompany.android.concurrency.consoleexamples

import com.yourcompany.android.concurrency.model.DogBreed
import com.yourcompany.android.concurrency.model.dogBreeds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    println("Start of main function")
    runBlocking {
        val job1 = launch {
            doSuspendingOperation()
        }

        val job2 = launch {
            doAnotherSuspendingOperation()
        }

        // Wait for both coroutines to complete
        job1.join()
        job2.join()

        println("All suspending operations completed")
    }
    println("End of main function")
}

suspend fun doSuspendingOperation() {
    // Simulate a long-running task
    println("Thread 1 is running.")
    delay(5000) // Suspending the thread for 5 seconds
    println("Thread 1 is suspending.")
}

suspend fun doAnotherSuspendingOperation() {
    // Simulate another long-running task
    println("Fetching dog breeds on Thread 2")
    delay(1000) // Simulating long running process
    println("Thread to fetched next dog breeds: " + dogBreeds.map(DogBreed::name).joinToString())
}

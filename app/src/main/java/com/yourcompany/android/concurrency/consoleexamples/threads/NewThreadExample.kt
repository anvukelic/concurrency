package com.yourcompany.android.concurrency.consoleexamples.threads

import com.yourcompany.android.concurrency.model.DogBreed
import com.yourcompany.android.concurrency.model.dogBreeds
import kotlin.concurrent.thread

// Example that runs code on the thread
fun main() {
    println("I am starting some work on ${Thread.currentThread()}")

    fetchDogBreeds { dogBreedsResult ->
        println("\nHere are the available dog breeds: ${dogBreedsResult.joinToString()}")
        println("Okay, now I'am actually done, I was waiting for ${Thread.currentThread()} to finish.")
    }

    Thread.sleep(50)

    println("\nI am done with work on ${Thread.currentThread()}, but wait, black magic is in progress! Woosh Woosh Woosh...")
}

private fun fetchDogBreeds(onResult: (List<String>) -> Unit) {
    thread {
        // Simulate network request
        println("Network request started on ${Thread.currentThread()}...")
        Thread.sleep(3000)
        onResult(dogBreeds.map(DogBreed::name))
    }
}

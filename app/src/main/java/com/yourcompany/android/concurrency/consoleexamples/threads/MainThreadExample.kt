package com.yourcompany.android.concurrency.consoleexamples.threads

import com.yourcompany.android.concurrency.model.DogBreed
import com.yourcompany.android.concurrency.model.dogBreeds

// Example that blocks main thread
fun main() {
    val dogBreeds = fetchDogBreeds().joinToString()
    println("Here are the available dog breeds: $dogBreeds")
    println("\nI'am done, please go to the next example \"NewThreadExample.kt\"")
}

private fun fetchDogBreeds(): List<String> {
    // Simulate network request
    println("Network request started...")
    Thread.sleep(3000)
    return dogBreeds.map(DogBreed::name)
}

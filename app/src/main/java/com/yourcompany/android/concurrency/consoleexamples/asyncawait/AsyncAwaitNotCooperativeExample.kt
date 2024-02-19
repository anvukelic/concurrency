package com.yourcompany.android.concurrency.consoleexamples.asyncawait

import com.yourcompany.android.concurrency.model.DogBreed
import com.yourcompany.android.concurrency.model.dogBreeds
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val searchTerm = "Sam"

    val searchForDogBreedJob = GlobalScope.launch {
        println("Search started on Coroutine ${this}\n")
        val deferredSearchResult = searchDogBreeds(searchTerm)
        println("Search result is $deferredSearchResult but it has to be unwrapped\n")
        val searchResult = deferredSearchResult.await()
        println(
            "For \"$searchTerm\" we found next dog breeds: ${
                searchResult.map(DogBreed::name).joinToString()
            }"
        ) // Will not execute since job is cancelled
    }

    Thread.sleep(500)
    searchForDogBreedJob.cancel()
    println("Parent job $searchForDogBreedJob has been cancelled!\n")
    Thread.sleep(5000)
    println("\nI am done, please go to the next example \"AsnycAwaitParentCancellationExample.kt\"")
}

/** Simulate network call to fetch dog breeds that contains [searchTerm] */
private fun searchDogBreeds(searchTerm: String) =
    GlobalScope.async {
        repeat(3) {
            delay(1000)
            println("Waiting for simulated request to finish...")
        }
        filterDogBreed(searchTerm).also { println("Coroutine ${this} Response: $it") }
    }

private fun filterDogBreed(searchTerm: String): List<DogBreed> =
    dogBreeds.filter { dogBreed -> dogBreed.name.contains(other = searchTerm, ignoreCase = true) }

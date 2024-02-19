package com.yourcompany.android.concurrency.consoleexamples.asyncawait

import com.yourcompany.android.concurrency.model.DogBreed
import com.yourcompany.android.concurrency.model.dogBreeds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val searchTerm = "Sam"

    val searchForDogBreedJob = GlobalScope.launch {
        val deferredSearchResult = searchDogBreeds(searchTerm, this)
        println("Search result is $deferredSearchResult but it has to be unwrapped\n")
        val searchResult = deferredSearchResult.await()
        println("For \"$searchTerm\" we found next dog breeds: ${searchResult.map(DogBreed::name).joinToString()}")
    }

    Thread.sleep(500)
    println("Cancelling parent job...")
    searchForDogBreedJob.cancel()
    Thread.sleep(5000)
    println("\nI am going back to sleep!")
}

/** Simulate network call to fetch dog breeds that contains [searchTerm] */
private fun searchDogBreeds(searchTerm: String, coroutineScope: CoroutineScope) =
    coroutineScope.async {
        repeat(3) {
            delay(1000)
            // Code will never execute from here since parent coroutine is cancelled while network request was in progress
            println("Waiting for simulated request to finish...")
        }
        filterDogBreed(searchTerm)
    }

private fun filterDogBreed(searchTerm: String): List<DogBreed> =
    dogBreeds.filter { dogBreed -> dogBreed.name.contains(other = searchTerm, ignoreCase = true) }

package com.yourcompany.android.concurrency.consoleexamples.asyncawait

import com.yourcompany.android.concurrency.model.DogBreed
import com.yourcompany.android.concurrency.model.dogBreeds
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val searchTerm = "Sam"

    GlobalScope.launch {
        val deferredSearchResult = searchDogBreeds(searchTerm)
        println("Search result is $deferredSearchResult but it has to be unwrapped\n")
        repeat(3) {
            delay(1000)
            println("Waiting for simulated request to finish...")
        }
        val searchResult = deferredSearchResult.await()
        println("For \"$searchTerm\" we found next dog breeds: ${searchResult.map(DogBreed::name).joinToString()}")
    }

    Thread.sleep(5000)
    println("\nI am done, please go to the next example \"AsnycAwaitParentCancellationFailedExample.kt\"")
}

/** Simulate network call to fetch dog breeds that contains [searchTerm] */
private fun searchDogBreeds(searchTerm: String) = GlobalScope.async {
    filterDogBreed(searchTerm)
}

private fun filterDogBreed(searchTerm: String): List<DogBreed> =
    dogBreeds.filter { dogBreed -> dogBreed.name.contains(other = searchTerm, ignoreCase = true) }

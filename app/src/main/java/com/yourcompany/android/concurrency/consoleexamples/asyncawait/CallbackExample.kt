package com.yourcompany.android.concurrency.consoleexamples.asyncawait

import com.yourcompany.android.concurrency.model.DogBreed
import com.yourcompany.android.concurrency.model.dogBreeds

fun main() {
    val searchTerm = "Sam"

    searchDogBreeds(searchTerm) { searchResult ->
        if (searchResult.isEmpty()) {
            println("For $searchTerm we haven't found any dog breeds.")
            return@searchDogBreeds
        }
        println("For $searchTerm, we found next result: ${searchResult.map(DogBreed::name).joinToString()}")
    }
    Thread.sleep(1000)
    println("\nI am done, please go to the next example \"AsnycAwaitParentExample.kt\"")
}

/** Simulate network call to fetch dog breeds that contains [searchTerm] */
private fun searchDogBreeds(searchTerm: String, onSearchResult: (List<DogBreed>) -> Unit) {
    Thread.sleep(3000)
    val searchResult = filterDogBreed(searchTerm)
    onSearchResult(searchResult)
}

private fun filterDogBreed(searchTerm: String): List<DogBreed> =
    dogBreeds.filter { dogBreed -> dogBreed.name.contains(other = searchTerm, ignoreCase = true) }

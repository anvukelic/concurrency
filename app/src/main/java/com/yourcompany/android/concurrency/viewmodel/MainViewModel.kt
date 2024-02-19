package com.yourcompany.android.concurrency.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yourcompany.android.concurrency.model.DogBreed
import com.yourcompany.android.concurrency.model.dogBreeds
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.lang.IllegalArgumentException
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    private var bgScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var searchJob: Job? = null

    fun searchDogBreeds(query: String, onResult: (Result<List<DogBreed>>) -> Unit) {
        if (query.isEmpty()) {
            bgScope.launch {
                val dogBreeds = fetchDogBreeds(query)
                onResult(Result.success(dogBreeds))
            }
        }
        // Main thread blocked
        if (query.equals("sam", true)) {
            Thread.sleep(10000)
        }
        // Async await return value
        if (query.equals("ger", true)) {
            bgScope.launch {
                val dogBreedsDeferred = bgScope.async { fetchDogBreeds(query) }
                val dogBreeds = dogBreedsDeferred.await()
                onResult(Result.success(dogBreeds))
            }
        }
        if (query.equals("hus", true)) {
            bgScope.launch {
                val validationJob = bgScope.launch {
                    throw IllegalArgumentException("Validation failed")
                }
                validationJob.join()
                val dogBreedsDeferred = bgScope.async { fetchDogBreeds(query) }
                try {
                    dogBreedsDeferred.await()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        /*searchJob?.cancel()
    searchJob = bgScope.launch {
        try {
            val dogBreeds = fetchDogBreeds(query)
            onResult(Result.success(dogBreeds))
        } catch (e: CancellationException) {
            Log.w(this@MainViewModel.toString(), e.message.toString())
        } catch (e: Exception) {
            onResult(Result.failure(e))
        }
    }*/
    }

    private suspend fun fetchDogBreeds(query: String): List<DogBreed> {
        // Simulate fetching dog breeds from a remote API
        delay(1000) // Simulating network delay
        // Simulate occasional network error
        if (query.contains(other = "error", ignoreCase = true)) {
            throw IOException("Failed to fetch data. Network error occurred.")
        }
        return dogBreeds
            .filter { dogBreed -> query.isEmpty() || dogBreed.name.contains(other = query, ignoreCase = true) }
    }
}

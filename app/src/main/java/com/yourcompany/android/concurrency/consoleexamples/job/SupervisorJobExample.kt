package com.yourcompany.android.concurrency.consoleexamples.job

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val supervisorJob = SupervisorJob()
    val scope = CoroutineScope(coroutineContext + supervisorJob)

    val job1 = scope.launch { updateUIPart1() }
    val job2 = scope.launch { updateUIPart2() }
    val job3 = scope.launch { updateUIPart3() }

    joinAll(job1, job2, job3)
}

suspend fun updateUIPart1() {
    delay(1000L)
    println("UI Part 1 updated")
}

suspend fun updateUIPart2() {
    delay(1000L)
    throw RuntimeException("UI Part 2 update failed")
}

suspend fun updateUIPart3() {
    delay(1000L)
    println("UI Part 3 updated")
}

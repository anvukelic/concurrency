package com.yourcompany.android.concurrency.consoleexamples.dispatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    log("Started in Main")
    withContext(Dispatchers.Default) {
        log("Working in default")
        delay(2000)
        log("Finished in default")
    }
    log("Back to Main")
}

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

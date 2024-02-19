package com.yourcompany.android.concurrency

import android.app.Application
import com.yourcompany.android.concurrency.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ConcurrencyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@ConcurrencyApplication)
            // Load modules
            modules(viewModelModule)
        }
    }
}

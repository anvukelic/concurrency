package com.yourcompany.android.concurrency.di

import com.yourcompany.android.concurrency.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel() }
}

package dev.ranjan.androidnewlearnings.di

import dev.ranjan.androidnewlearnings.ui.TheViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/*
Here define the ViewModel instance things
 */
val viewModelModule = module {
    viewModel {
        TheViewModel(get())
    }
}
package com.sibelyildiz.cryptocurrencyapp.di

import com.sibelyildiz.cryptocurrencyapp.data.repository.Repository
import com.sibelyildiz.cryptocurrencyapp.ui.home.MainViewModel
import com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get()) }

    single { Repository(get()) }
}
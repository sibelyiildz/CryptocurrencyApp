package com.sibelyildiz.cryptocurrencyapp.di

import com.sibelyildiz.cryptocurrencyapp.data.repository.Repository
import com.sibelyildiz.cryptocurrencyapp.ui.home.MainViewModel
import com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home.HomeViewModel
import com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home.coinDetail.CoinDetailViewModel
import com.sibelyildiz.cryptocurrencyapp.ui.loginRegister.login.LoginViewModel
import com.sibelyildiz.cryptocurrencyapp.ui.loginRegister.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { CoinDetailViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }

    single { Repository(get()) }
}
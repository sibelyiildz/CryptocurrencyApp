package com.sibelyildiz.cryptocurrencyapp.data.repository

import com.sibelyildiz.cryptocurrencyapp.data.service.ApiService

class Repository(private val apiService: ApiService) {

    suspend fun getCoinList() = apiService.getCoinList()

    suspend fun getCoinDetail(id: String) = apiService.getCoinDetail(id)
}
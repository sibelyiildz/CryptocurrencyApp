package com.sibelyildiz.cryptocurrencyapp.data.service

import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
import retrofit2.http.GET

interface ApiService {

    @GET("coins/list")
    suspend fun getCoinList(): List<CoinListResponse>
}
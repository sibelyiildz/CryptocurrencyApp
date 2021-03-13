package com.sibelyildiz.cryptocurrencyapp.data.service

import com.sibelyildiz.cryptocurrencyapp.data.model.CoinDetailResponse
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("coins/markets")
    suspend fun getCoinList(
        @Query("vs_currency") vs_currency: String = "try",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") per_page: Int = 50,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): List<CoinListResponse>

    @GET("coins/{id}")
    suspend fun getCoinDetail(
        @Path("id") id: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") market_data: Boolean = true,
        @Query("community_data") community_data: Boolean = false,
        @Query("developer_data") developer_data: Boolean = false,
    ): CoinDetailResponse
}
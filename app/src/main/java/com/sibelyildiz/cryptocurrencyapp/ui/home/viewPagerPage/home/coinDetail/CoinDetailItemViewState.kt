package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home.coinDetail

import com.sibelyildiz.cryptocurrencyapp.data.model.CoinDetailResponse
import java.text.NumberFormat

data class CoinDetailItemViewState(val coin: CoinDetailResponse) {
    fun getName() = coin.name

    fun getSymbol() = "(" + coin.symbol + ")"

    fun getImageUrl() = coin.image?.large

    fun getCurrentPrice() = coin.market_data?.current_price?.type.toString()

    fun getPriceChange(): String {
        val nf: NumberFormat = NumberFormat.getInstance()
        nf.maximumFractionDigits = 2
        return nf.format(coin.market_data?.price_change_percentage_24h) + "%"
    }

    fun getDescription() = coin.description?.en

    fun getHashingAlgorithm() = coin.hashing_algorithm ?: "Algoritma bulunamadı"
}
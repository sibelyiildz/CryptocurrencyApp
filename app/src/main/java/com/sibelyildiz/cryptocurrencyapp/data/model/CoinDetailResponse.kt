package com.sibelyildiz.cryptocurrencyapp.data.model

import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(
    val id: String?,
    val symbol: String?,
    val name: String?,
    val hashing_algorithm: String?,
    val description: Description?,
    val image: Image?,
    val last_updated: String?,
    val market_data: MarketData?,
)

data class Description(
    val en: String
)

data class Image(
    val large: String
)

data class MarketData(
    val current_price: CurrentPrice?,
    val price_change_percentage_24h: Double?
)

data class CurrentPrice(
    @SerializedName("try")
    val type: Double?
)



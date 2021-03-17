package com.sibelyildiz.cryptocurrencyapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinListResponse(
    val current_price: Double?,
    val id: String? = "",
    val image: String? = "",
    val name: String? = "",
    val price_change_24h: Double? = 0.0
) : Parcelable {
    constructor() : this(0.0)
}
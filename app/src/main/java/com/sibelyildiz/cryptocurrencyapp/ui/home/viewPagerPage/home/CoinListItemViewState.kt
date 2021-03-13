package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home

import android.content.Context
import androidx.core.content.ContextCompat
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
import java.text.NumberFormat

data class CoinListItemViewState(val coins: CoinListResponse) {
    fun getName() = coins.name

    fun getImageUrl() = coins.image

    fun getCurrentPrice() = coins.current_price.toString()

    fun getPriceChange(): String {
        val nf: NumberFormat = NumberFormat.getInstance()
        nf.maximumFractionDigits = 2
        return nf.format(coins.price_change_24h) + "%"
    }

    fun textViewBackground(context: Context): Int {
        val firstIndex = coins.price_change_24h.toString().substring(0, 1)
        return if (firstIndex == "-") {
            ContextCompat.getColor(context, R.color.black)

        } else {
            ContextCompat.getColor(context, R.color.color_blue)
        }
    }

}
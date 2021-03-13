package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home.coinDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sibelyildiz.cryptocurrencyapp.data.repository.Repository
import com.sibelyildiz.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.Dispatchers

class CoinDetailViewModel(private val repository: Repository) : ViewModel() {

    var id = ""
    fun getCoinDetail() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getCoinDetail(id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Failure!"))
        }
    }
}
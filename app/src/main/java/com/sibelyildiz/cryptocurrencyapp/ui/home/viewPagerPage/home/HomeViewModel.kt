package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sibelyildiz.cryptocurrencyapp.data.repository.Repository
import com.sibelyildiz.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repository: Repository) : ViewModel() {

    fun getCoinList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getCoinList()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Failure!"))
        }
    }
}
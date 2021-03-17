package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
import com.sibelyildiz.cryptocurrencyapp.data.repository.Repository

class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    private val _getFavoriteCoins: MutableLiveData<ArrayList<CoinListResponse>> = MutableLiveData()
    val getFavoriteCoins: LiveData<ArrayList<CoinListResponse>> = _getFavoriteCoins

    private val _getFavoriteCoinsError: MutableLiveData<String> = MutableLiveData()
    val getFavoriteCoinsError: LiveData<String> = _getFavoriteCoinsError

    fun getCoins() {
        val coinList = ArrayList<CoinListResponse>()
        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .collection("favorite")
            .get()
            .addOnSuccessListener {
                for (n in it.documents) {
                    val data = n.toObject(CoinListResponse::class.java)
                    data?.let { it1 -> coinList.add(it1) }
                }
                _getFavoriteCoins.value = coinList
            }
            .addOnFailureListener {
                _getFavoriteCoinsError.value = it.toString()
            }
    }

}
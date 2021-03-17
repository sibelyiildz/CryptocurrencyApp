package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home.coinDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
import com.sibelyildiz.cryptocurrencyapp.data.repository.Repository
import com.sibelyildiz.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.Dispatchers

class CoinDetailViewModel(private val repository: Repository) : ViewModel() {
    val mFireStore = FirebaseFirestore.getInstance()

    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var _addFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val addFavorite: LiveData<Boolean> = _addFavorite

    private var _deleteFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val deleteFavorite: LiveData<Boolean> = _deleteFavorite


    var id = ""
    fun getCoinDetail() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getCoinDetail(id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Failure!"))
        }
    }

    fun getIsFovorite() {
        mFireStore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .collection("favorite")
            .document(id)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    _isFavorite.value = true
                }
            }
            .addOnFailureListener {
            }
    }

    var coinListResponse = CoinListResponse()
    fun addFavorite() {
        mFireStore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .collection("favorite")
            .document(id)
            .set(coinListResponse)
            .addOnSuccessListener {
                _addFavorite.value = true
            }
            .addOnFailureListener {
                _addFavorite.value = false
            }
    }

    fun deleteFavorite() {
        mFireStore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .collection("favorite")
            .document(id)
            .delete()
            .addOnSuccessListener {
                _deleteFavorite.value = true
            }
            .addOnFailureListener {
                _deleteFavorite.value = false
            }
    }

}
package com.sibelyildiz.cryptocurrencyapp.ui.loginRegister.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sibelyildiz.cryptocurrencyapp.data.repository.Repository

class RegisterViewModel(val repository: Repository) : ViewModel() {

    private val _userRegister: MutableLiveData<Boolean> = MutableLiveData()
    val userRegister: LiveData<Boolean> = _userRegister

    private val _mainSend: MutableLiveData<Boolean> = MutableLiveData()
    val mainSend: LiveData<Boolean> = _mainSend


    fun register(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        _userRegister.value = true

                        FirebaseAuth.getInstance().signOut()
                    } else {
                        _userRegister.value = false

                    }
                }
    }

    fun mailSend() {
        FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnCompleteListener { p0 ->
            if (p0.isSuccessful) {
                _mainSend.value = true
            } else {
                _mainSend.value = true
            }
        }
    }
}
package com.sibelyildiz.cryptocurrencyapp.ui.loginRegister.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sibelyildiz.cryptocurrencyapp.data.repository.Repository

class LoginViewModel(val repository: Repository) : ViewModel() {

    private val _sigIn: MutableLiveData<Boolean> = MutableLiveData()
    val sigIn: LiveData<Boolean> = _sigIn

    private val _mailConfirm: MutableLiveData<Boolean> = MutableLiveData()
    val mailConfirm: LiveData<Boolean> = _mailConfirm

    fun signIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            _sigIn.value = it.isSuccessful
        }
    }

    fun mailConfirmation() {
        FirebaseAuth.AuthStateListener { p0 ->
            val user = p0.currentUser
            if (user != null) {
                _mailConfirm.value = user.isEmailVerified
            }
        }
    }

}
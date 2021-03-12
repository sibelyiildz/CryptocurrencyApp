package com.sibelyildiz.cryptocurrencyapp.ui.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentLoginBinding
import com.sibelyildiz.cryptocurrencyapp.util.toastMessage

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListener()
        initAuthStateListener()
    }

    private fun clickListener() {
        binding.btnLogin.setOnClickListener(loginButtonSetClickListener)
    }


    private val loginButtonSetClickListener = View.OnClickListener {
        if (binding.edtEmail.text.isNotEmpty() && binding.edtPassword.text.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString()
            )
                    .addOnCompleteListener { p0 ->
                        if (p0.isSuccessful) {
                            findNavController().navigate(R.id.homeFragment)
                        } else {
                            "Hatalı Giriş: " + p0.exception?.message!!.toastMessage(requireContext())

                        }
                    }
        } else {
            "Boş alanları doldurunuz".toastMessage(requireContext())
        }
    }

    private fun initAuthStateListener() {
        mAuthStateListener = FirebaseAuth.AuthStateListener { p0 ->
            val user = p0.currentUser
            if (user != null) {
                if (user.isEmailVerified) {
                    "Mail onaylandı giriş yapabilirsiniz".toastMessage(requireContext())
                } else {
                    "Mail adresinizi onaylamadan giriş yapamazsınız".toastMessage(requireContext())
                }
            }
        }
    }

}
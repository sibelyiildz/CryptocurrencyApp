package com.sibelyildiz.cryptocurrencyapp.ui.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentRegisterBinding
import com.sibelyildiz.cryptocurrencyapp.util.toastMessage

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListener()
    }

    private fun clickListener() {
        binding.btnRegister.setOnClickListener(registerButtonSetClickListener)
    }

    private val registerButtonSetClickListener = View.OnClickListener {
        if (binding.edtEmail.text.isNotEmpty() && binding.edtPassword.text.isNotEmpty()) {
            userRegister(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
        } else {
            //context paslamak doğru mu
            "Boş alanları doldurunuz!".toastMessage(requireContext())
        }
    }

    private fun userRegister(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        "Üye kaydedildi:" + FirebaseAuth.getInstance().currentUser?.email!!.toastMessage(requireContext())
                        sendEmail()
                        FirebaseAuth.getInstance().signOut()
                    } else {
                        "Üye kaydedilirken sorun oluştu:" + p0.exception?.message!!.toastMessage(requireContext())
                    }
                }
    }

    private fun sendEmail() {
        val user = auth.currentUser

        user?.sendEmailVerification()?.addOnCompleteListener(object : OnCompleteListener<Void> {
            override fun onComplete(p0: Task<Void>) {
                if (p0.isSuccessful) {
                    "Mailinizi kontrol edin, mailinizi onaylayın".toastMessage(requireContext())
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.loginFragment)
                } else {
                    "Mail gönderilirken sorun oluştu " + p0.exception?.message!!.toastMessage(requireContext())
                }
            }

        })
    }


}
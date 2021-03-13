package com.sibelyildiz.cryptocurrencyapp.ui.loginRegister.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentRegisterBinding
import com.sibelyildiz.cryptocurrencyapp.util.toastMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private val viewModel by viewModel<RegisterViewModel>()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater)
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

            viewModel.register(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
            viewModel.userRegister.observe(this) {
                if (it) {
                    "Üye kaydedildi" + FirebaseAuth.getInstance().currentUser?.email!!.toastMessage(requireActivity())
                    sendEmail()
                } else {
                    "Üye kaydedilirken sorun oluştu".toastMessage(requireActivity())
                }
            }

        } else {
            "Boş alanları doldurunuz!".toastMessage(requireActivity())
        }
    }

    private fun sendEmail() {
        viewModel.mailSend()
        viewModel.mainSend.observe(this) {
            if (it) {
                "Mailinizi kontrol edin, mailinizi onaylayın".toastMessage(requireActivity())
                findNavController().popBackStack()
                findNavController().navigate(R.id.loginFragment)
            } else {
                "Mail gönderilirken sorun oluştu ".toastMessage(requireActivity())

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
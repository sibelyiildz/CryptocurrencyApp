package com.sibelyildiz.cryptocurrencyapp.ui.loginRegister.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentLoginBinding
import com.sibelyildiz.cryptocurrencyapp.util.toastMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel by viewModel<LoginViewModel>()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAuthStateListener()
        clickListener()
    }

    private fun initAuthStateListener() {
        viewModel.mailConfirmation()
        viewModel.mailConfirm.observe(viewLifecycleOwner) {
            if (it) {
                "Mail onaylandı giriş yapabilirsiniz".toastMessage(requireActivity())
            } else {
                "Mail adresinizi onaylamadan giriş yapamazsınız".toastMessage(requireActivity())
            }
        }
    }

    private fun clickListener() {
        binding.btnLogin.setOnClickListener(loginButtonSetClickListener)
    }

    private val loginButtonSetClickListener = View.OnClickListener {
        if (binding.edtEmail.text.isNotEmpty() && binding.edtPassword.text.isNotEmpty()) {
            viewModel.signIn(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
            viewModel.sigIn.observe(this) {
                if (it) {
                    "Başarılı giriş".toastMessage(requireActivity())
                    findNavController().navigate(R.id.mainFragment, null, NavOptions.Builder().setPopUpTo(R.id.loginRegisterMainFragment, true).build())
                } else {
                    "Hatalı giriş".toastMessage(requireActivity())
                }
            }
        } else {
            "Boş alanları doldurunuz".toastMessage(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
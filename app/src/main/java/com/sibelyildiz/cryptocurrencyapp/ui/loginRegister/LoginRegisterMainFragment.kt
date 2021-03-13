package com.sibelyildiz.cryptocurrencyapp.ui.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentLoginRegisterMainBinding

class LoginRegisterMainFragment : Fragment() {

    private var _binding: FragmentLoginRegisterMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginRegisterMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListener()
    }

    private fun clickListener() {
        binding.btnLogin.setOnClickListener(loginButtonSetClickListener)
        binding.btnRegister.setOnClickListener(registerButtonSetClickListener)
    }

    private val loginButtonSetClickListener = View.OnClickListener {
        findNavController().navigate(R.id.loginFragment)
    }

    private val registerButtonSetClickListener = View.OnClickListener {
        findNavController().navigate(R.id.registerFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
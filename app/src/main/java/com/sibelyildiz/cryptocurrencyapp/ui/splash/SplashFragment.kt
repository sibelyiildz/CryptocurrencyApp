package com.sibelyildiz.cryptocurrencyapp.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.splashAnimation.playAnimation()

        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                findNavController().popBackStack()
                if (auth.currentUser != null) {
                    findNavController().navigate(R.id.mainFragment)
                } else {
                    findNavController().navigate(R.id.loginRegisterMainFragment)
                }
            }

            override fun onTick(millisUntilFinished: Long) {}
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}
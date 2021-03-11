package com.sibelyildiz.cryptocurrencyapp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.splashAnimation.playAnimation()

        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                findNavController().popBackStack()
                findNavController().navigate(R.id.homeFragment)
            }

            override fun onTick(millisUntilFinished: Long) {}
        }.start()
    }

    //Fragment kapantıdğında obje arkaplandan silinmeli
    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.onFinish()
    }
}
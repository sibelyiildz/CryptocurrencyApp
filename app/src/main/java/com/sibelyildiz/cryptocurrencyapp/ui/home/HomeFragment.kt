package com.sibelyildiz.cryptocurrencyapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.util.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCoinList().observe(viewLifecycleOwner) { state ->
            when (state.status) {
                Status.LOADING -> {
                    Log.e("TEST", "LOADING ${state.data}")
                }
                Status.SUCCESS -> {
                    Log.e("TEST", "SUCCESS ${state.data}")
                }
                Status.ERROR -> {
                    Log.e("TEST", "ERROR ${state.message}")
                }
            }
        }
    }

}
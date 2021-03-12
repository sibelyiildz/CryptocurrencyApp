package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentHomeBinding
import com.sibelyildiz.cryptocurrencyapp.util.Resource
import com.sibelyildiz.cryptocurrencyapp.util.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel by viewModel<HomeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setObservers()
    }

    private fun setObservers() {

        viewModel.getCoinList().observe(viewLifecycleOwner, getCoinListObserver)
    }

    private val getCoinListObserver = Observer<Resource<List<CoinListResponse>>> { state ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
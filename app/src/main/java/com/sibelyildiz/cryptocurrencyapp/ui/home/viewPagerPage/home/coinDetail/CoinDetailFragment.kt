package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home.coinDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinDetailResponse
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentCoinDetailBinding
import com.sibelyildiz.cryptocurrencyapp.util.Resource
import com.sibelyildiz.cryptocurrencyapp.util.Status
import com.sibelyildiz.cryptocurrencyapp.util.toastMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<CoinDetailViewModel>()
    private var coinID = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coinID = requireArguments().getString("coinID").toString()
        Log.e("TEST", coinID)
        _binding = FragmentCoinDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setObserver()
        setClickListener()
    }

    private fun setObserver() {
        viewModel.id = coinID
        viewModel.getCoinDetail().observe(viewLifecycleOwner, getCoinDetailObserver)
    }

    private fun setClickListener() {
        binding.btnRefreshSave.setOnClickListener {
            if (binding.edtRefresh.text.isNullOrEmpty()) {
                "Lütfen bir değer girin".toastMessage(requireActivity())
            } else {
                val data = bundleOf("period" to binding.edtRefresh.text.toString())
                findNavController().popBackStack()
                findNavController().navigate(R.id.homeFragment, data)
            }
        }
    }

    private val getCoinDetailObserver = Observer<Resource<CoinDetailResponse>> { state ->
        when (state.status) {
            Status.LOADING -> {
                Log.e("TEST", "LOADING ${state.data}")
            }
            Status.SUCCESS -> {
                state.data?.let {
                    Log.e("TEST", "SUCCESS ${state.data}")
                    binding.coinDateilViewState = CoinDetailItemViewState(it)

                }
            }
            Status.ERROR -> {
                Log.e("TEST", "ERROR ${state.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
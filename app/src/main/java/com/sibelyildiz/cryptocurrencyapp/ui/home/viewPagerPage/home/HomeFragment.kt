package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentHomeBinding
import com.sibelyildiz.cryptocurrencyapp.util.Resource
import com.sibelyildiz.cryptocurrencyapp.util.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var adapter: CoinRecylerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setObservers()
        setClickListener()
        loadRecycler()
    }

    private fun setObservers() {
        viewModel.getCoinList().observe(viewLifecycleOwner, getCoinListObserver)
    }

    private fun setClickListener() {
        binding.edtSarch.addTextChangedListener(searchChangedListener)
    }

    private fun loadRecycler() {
        adapter = CoinRecylerAdapter()
        binding.recyclerViewCoins.adapter = adapter
        adapter.onClickItem {
            findNavController().navigate(R.id.coinDetailFragment)
        }
    }

    private val getCoinListObserver = Observer<Resource<List<CoinListResponse>>> { state ->
        when (state.status) {
            Status.LOADING -> {
                Log.e("TEST", "LOADING ${state.data}")
            }
            Status.SUCCESS -> {
                state.data?.let {
                    Log.e("TEST", "SUCCESS ${state.data}")
                    adapter.coins.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
            Status.ERROR -> {
                Log.e("TEST", "ERROR ${state.message}")
            }
        }
    }

    private val searchChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            adapter.filter.filter(s.toString())
            adapter.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
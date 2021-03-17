package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentHomeBinding
import com.sibelyildiz.cryptocurrencyapp.util.Resource
import com.sibelyildiz.cryptocurrencyapp.util.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var adapter: CoinRecylerAdapter
    private var refreshPeriod = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (arguments != null && requireArguments().containsKey("period")) {
            refreshPeriod = requireArguments().getString("period").toString()
        }
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

        if (refreshPeriod.isNotEmpty()) {
            refreshPeriod.toIntOrNull()?.let { setRefreshPeriod(it) }
        }
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
            val data = bundleOf("coinID" to it.id)
            findNavController().navigate(R.id.coinDetailFragment, data)
        }
    }

    private val getCoinListObserver = Observer<Resource<List<CoinListResponse>>> { state ->
        when (state.status) {
            Status.LOADING -> {
                binding.homeProgress.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                binding.homeProgress.visibility = View.GONE
                state.data?.let {
                    adapter.coins.clear()
                    adapter.coins.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
            Status.ERROR -> {
                binding.homeProgress.visibility = View.GONE
            }
        }
    }

    private val searchChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            //seacrh işlemi
            adapter.filter.filter(s.toString())
            adapter.notifyDataSetChanged()
        }

    }

    private fun setRefreshPeriod(period: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                viewModel.getCoinList().observe(viewLifecycleOwner, getCoinListObserver)
                val delay = period * 60000
                delay(delay.toLong())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
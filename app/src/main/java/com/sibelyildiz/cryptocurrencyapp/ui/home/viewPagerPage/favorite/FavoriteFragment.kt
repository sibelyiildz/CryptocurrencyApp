package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentFavoriteBinding
import com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home.CoinRecylerAdapter
import com.sibelyildiz.cryptocurrencyapp.util.toastMessage
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    private val viewModel by viewModel<FavoriteViewModel>()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CoinRecylerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        setRecycler()
        setObservers()
    }

    private fun setRecycler() {
        adapter = CoinRecylerAdapter()
        binding.recyclerViewCoins.adapter = adapter
        adapter.onClickItem {
            val data = bundleOf("coinID" to it.id)
            findNavController().navigate(R.id.coinDetailFragment, data)
        }
    }

    private fun setObservers() {
        viewModel.getCoins()
        viewModel.getFavoriteCoins.observe(viewLifecycleOwner) { coinList ->
            binding.progressBar.visibility = View.GONE

            adapter.coins.clear()
            adapter.coins.addAll(coinList)
            adapter.notifyDataSetChanged()
        }
        viewModel.getFavoriteCoinsError.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            "Coinler yüklenirler bir sorun oluştu :(".toastMessage(requireActivity())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
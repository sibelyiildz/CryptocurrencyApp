package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home.coinDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinDetailResponse
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
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
    private var coinImage = ""
    private var priceChange = ""
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coinID = requireArguments().getString("coinID").toString()
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
        loadInitData()
    }

    private fun setObserver() {
        viewModel.getCoinDetail().observe(viewLifecycleOwner, getCoinDetailObserver)
    }

    private fun setClickListener() {
        binding.btnRefreshSave.setOnClickListener(btnRefreshSaveClickListener)
        binding.imgFavorite.setOnClickListener(imgFavoriteClickListener)
    }

    private fun loadInitData() {
        viewModel.id = coinID

        //Bu coin favoriye eklenmişse gerekli setleme işlemi
        viewModel.getIsFovorite()
        viewModel.isFavorite.observe(viewLifecycleOwner) {
            if (it) {
                binding.imgFavorite.setColorFilter(requireActivity().resources.getColor(R.color.cream_can))
                isFavorite = true
            }
        }
    }

    private val getCoinDetailObserver = Observer<Resource<CoinDetailResponse>> { state ->
        when (state.status) {
            Status.LOADING -> {
                binding.coinDetailProgress.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                state.data?.let {
                    binding.coinDetailProgress.visibility = View.GONE
                    binding.coinDateilViewState = CoinDetailItemViewState(it) //setup UI

                    coinImage = it.image?.large.toString()
                    priceChange = it.market_data?.price_change_percentage_24h.toString()
                }
            }
            Status.ERROR -> {
                binding.coinDetailProgress.visibility = View.GONE
            }
        }
    }

    //Home page'e git kullanıcının girdiği periodlarla sayfayı yenile
    private val btnRefreshSaveClickListener = View.OnClickListener {
        if (binding.edtRefresh.text.isNullOrEmpty()) {
            "Lütfen bir değer girin".toastMessage(requireActivity())
        } else {
            val data = bundleOf("period" to binding.edtRefresh.text.toString())
            findNavController().popBackStack()
            findNavController().navigate(R.id.homeFragment, data)
        }
    }

    private val imgFavoriteClickListener = View.OnClickListener {

        //Cloud'a kaydedilecek model
        val coinListResponse = CoinListResponse(
            binding.txtCurrentPrice.text.toString().toDouble(),
            coinID, coinImage,
            binding.txtName.text.toString(),
            priceChange.toDouble()
        )

        //favorilerde değilse favorilere ekle
        if (!isFavorite) {
            viewModel.coinListResponse = coinListResponse
            viewModel.addFavorite()
            viewModel.addFavorite.observe(viewLifecycleOwner) {
                if (it) {
                    "Favorilere eklendi".toastMessage(requireActivity())
                    binding.imgFavorite.setColorFilter(requireActivity().resources.getColor(R.color.cream_can))
                    isFavorite = true
                } else {
                    "Favorilere eklenirken sorun oluştu :( ".toastMessage(requireActivity())
                }
            }
            //favorilerdeyse favorilerde sil
        } else {
            viewModel.deleteFavorite()
            viewModel.deleteFavorite.observe(viewLifecycleOwner) {
                if (it) {
                    "Favorilerden silindi".toastMessage(requireActivity())
                    binding.imgFavorite.setColorFilter(requireActivity().resources.getColor(R.color.color_grey))
                    isFavorite = false
                } else {
                    "Favorilerden silinirken sorun oluştu :(".toastMessage(requireActivity())
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
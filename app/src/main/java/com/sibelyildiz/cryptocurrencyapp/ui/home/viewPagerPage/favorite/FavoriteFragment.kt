package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
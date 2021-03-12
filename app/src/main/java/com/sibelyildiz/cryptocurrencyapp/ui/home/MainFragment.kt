package com.sibelyildiz.cryptocurrencyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.databinding.FragmentMainBinding
import com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.favorite.FavoriteFragment
import com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private val viewModel by viewModel<MainViewModel>()
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        loadViewPager()
    }

    private fun loadViewPager() {
        val fragmentTitleList: MutableList<String> = ArrayList()
        fragmentTitleList.add(requireActivity().getString(R.string.home))
        fragmentTitleList.add(requireActivity().getString(R.string.favorite))

        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPagerAdapter.addFragment(HomeFragment())
        viewPagerAdapter.addFragment(FavoriteFragment())

        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentTitleList[position]
        }.attach()

    }

}
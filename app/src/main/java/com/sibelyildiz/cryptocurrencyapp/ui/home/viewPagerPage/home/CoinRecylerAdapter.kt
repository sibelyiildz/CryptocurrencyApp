package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
import com.sibelyildiz.cryptocurrencyapp.databinding.RowCoinBinding

class CoinRecylerAdapter : RecyclerView.Adapter<CoinRecylerAdapter.Holder>() {

    var coins = ArrayList<CoinListResponse>()

    private lateinit var coinClick: (CoinListResponse) -> Unit
    fun onClickItem(onClick: (CoinListResponse) -> Unit) {
        this.coinClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding: RowCoinBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_coin,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(coins[position], coinClick)
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    class Holder(val binding: RowCoinBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coins: CoinListResponse, coinClick: (CoinListResponse) -> Unit) {
            binding.data = CoinListItemViewState(coins)
            binding.root.setOnClickListener {
                coinClick.invoke(coins)
            }
        }
    }
}
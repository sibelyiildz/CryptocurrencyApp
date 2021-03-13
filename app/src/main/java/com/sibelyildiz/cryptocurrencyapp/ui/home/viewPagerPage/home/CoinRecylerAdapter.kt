package com.sibelyildiz.cryptocurrencyapp.ui.home.viewPagerPage.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sibelyildiz.cryptocurrencyapp.R
import com.sibelyildiz.cryptocurrencyapp.data.model.CoinListResponse
import com.sibelyildiz.cryptocurrencyapp.databinding.RowCoinBinding
import java.util.*
import kotlin.collections.ArrayList

class CoinRecylerAdapter : RecyclerView.Adapter<CoinRecylerAdapter.Holder>(), Filterable {

    var coins = ArrayList<CoinListResponse>()
    var coinsNewList: MutableList<CoinListResponse> = mutableListOf()

    init {
        coinsNewList = coins
    }

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
        holder.bind(coinsNewList[position], coinClick)
    }

    override fun getItemCount(): Int {
        return coinsNewList.size
    }

    class Holder(val binding: RowCoinBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coinsNewList: CoinListResponse, coinClick: (CoinListResponse) -> Unit) {
            binding.data = CoinListItemViewState(coinsNewList)
            binding.root.setOnClickListener {
                coinClick.invoke(coinsNewList)
            }
        }
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    coinsNewList = coins
                } else {
                    val resultList: MutableList<CoinListResponse> = mutableListOf()
                    for (row in coins) {
                        if (row.name!!.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) || row.id!!.toLowerCase(
                                Locale.ROOT
                            ).contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    coinsNewList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = coinsNewList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                coinsNewList = results?.values as MutableList<CoinListResponse>
                notifyDataSetChanged()
            }

        }
    }
}
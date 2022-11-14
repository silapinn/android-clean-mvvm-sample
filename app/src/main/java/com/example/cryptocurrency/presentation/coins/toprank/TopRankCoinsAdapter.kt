package com.example.cryptocurrency.presentation.coins.toprank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.example.cryptocurrency.databinding.ItemTopRankCoinBinding
import com.example.cryptocurrency.domain.model.Coin

class TopRankCoinsAdapter(
    val coins: List<Coin>
) : RecyclerView.Adapter<TopRankCoinsAdapter.TopRankCoinHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRankCoinHolder {
        val binding = ItemTopRankCoinBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TopRankCoinHolder(binding)
    }

    override fun onBindViewHolder(holder: TopRankCoinHolder, position: Int) {
        holder.bind(coins[position])
    }

    override fun getItemCount() = coins.size

    class TopRankCoinHolder(
        private val binding: ItemTopRankCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: Coin) {
            binding.symbolTextView.text = coin.symbol
            binding.nameTextView.text = coin.name
            binding.changeTextView.change = coin.change
            val imageLoader = ImageLoader.Builder(itemView.context)
                .components {
                    add(SvgDecoder.Factory())
                }
                .build()
            binding.iconImageView.load(coin.iconUrl, imageLoader)
        }
    }
}

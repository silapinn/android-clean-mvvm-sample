package com.example.cryptocurrency.presentation.coins

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.common.HorizontalSpaceItemDecoration
import com.example.cryptocurrency.databinding.*
import com.example.cryptocurrency.presentation.coins.toprank.TopRankCoinsAdapter
import java.lang.IllegalStateException
import kotlin.properties.Delegates

class CoinsAdapter : RecyclerView.Adapter<CoinsAdapter.CoinViewHolder>() {

    var coinListItems: List<CoinListItem> by Delegates.observable(emptyList()) { _, old, new ->
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return coinListItems[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            CoinItemType.SECTION_HEADLINE.ordinal -> {
                val binding = ItemSectionHeadlineBinding.inflate(layoutInflater, parent, false)
                return CoinViewHolder.Title(binding)
            }
            CoinItemType.TOP_RANK_CRYPTO.ordinal -> {
                val binding = ItemTopRankCryptoBinding.inflate(layoutInflater, parent, false)
                return CoinViewHolder.TopRankCrypto(binding)
            }
            CoinItemType.COIN.ordinal -> {
                val binding = ItemCoinBinding.inflate(layoutInflater, parent, false)
                return CoinViewHolder.Coin(binding)
            }
            CoinItemType.FRIEND_INVITE.ordinal -> {
                val binding = ItemFriendInviteBinding.inflate(layoutInflater, parent, false)
                return CoinViewHolder.FriendInvite(binding)
            }
            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val itemState = coinListItems[position]
        when (holder) {
            is CoinViewHolder.Title -> holder.bind(itemState as CoinListItem.SectionHeadline)
            is CoinViewHolder.TopRankCrypto -> holder.bind(itemState as CoinListItem.TopRankCrypto)
            is CoinViewHolder.Coin -> holder.bind(itemState as CoinListItem.CryptoCoin)
            is CoinViewHolder.FriendInvite -> holder.bind(itemState as CoinListItem.FriendInvite)
        }
    }

    override fun getItemCount() = coinListItems.size

    sealed class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context: Context
            get() = itemView.context

        class Title(
            private val binding: ItemSectionHeadlineBinding
        ) : CoinViewHolder(binding.root) {

            fun bind(headline: CoinListItem.SectionHeadline) {
                binding.headlineTextView.text = context.getString(headline.textResId)
            }
        }

        class TopRankCrypto(
            private val binding: ItemTopRankCryptoBinding
        ) : CoinViewHolder(binding.root) {

            val itemDecoration: HorizontalSpaceItemDecoration by lazy {
                HorizontalSpaceItemDecoration(
                    context.resources.getDimensionPixelOffset(
                        R.dimen.item_top_rank_coin_horizontal_space
                    )
                )
            }

            fun bind(topRankCrypto: CoinListItem.TopRankCrypto) {
                val headline = context.getString(
                    R.string.top_rank_crypto_section_headline,
                    topRankCrypto.coins.size
                )
                binding.topRankTextView.text = buildSpannedString {
                    append(headline)
                }
                binding.topRankCoinsRecyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    adapter = TopRankCoinsAdapter(topRankCrypto.coins)
                    removeItemDecoration(itemDecoration)
                    addItemDecoration(itemDecoration)
                }
            }
        }

        class Coin(private val binding: ItemCoinBinding) : CoinViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(coinListItem: CoinListItem.CryptoCoin) = with(coinListItem) {
                binding.nameTextView.text = coin.name
                binding.symbolTextView.text = coin.symbol
                binding.priceTextView.text = "$${coin.price}"
                binding.changeTextView.change = coin.change
            }
        }

        class FriendInvite(
            private val binding: ItemFriendInviteBinding
        ) : CoinViewHolder(binding.root) {

            fun bind(friendInvite: CoinListItem.FriendInvite) {
                binding.iconImageView.setImageResource(friendInvite.iconResId)
                binding.descriptionTextView.text = buildSpannedString {
                    append(context.getString(friendInvite.descriptionResId))
                    append(" ")
                    inSpans(ForegroundColorSpan(Color.BLUE)) {
                        append(context.getString(friendInvite.actionResId))
                    }
                }
            }
        }
    }
}
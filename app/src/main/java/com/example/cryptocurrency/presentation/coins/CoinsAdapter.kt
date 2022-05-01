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
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.common.HorizontalSpaceItemDecoration
import com.example.cryptocurrency.databinding.*
import com.example.cryptocurrency.presentation.coins.toprank.TopRankCoinsAdapter
import java.lang.IllegalStateException
import kotlin.properties.Delegates

class CoinsAdapter(private val retryCallback: () -> Unit) :
    RecyclerView.Adapter<CoinsAdapter.CoinViewHolder>() {

    private val differ: AsyncListDiffer<CoinListItem> = AsyncListDiffer(this, diffItemCallback)

    override fun getItemViewType(position: Int): Int {
        return differ.currentList[position].type.ordinal
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
            CoinItemType.LOADING.ordinal -> {
                val binding = ItemPaginationLoadingBinding.inflate(layoutInflater, parent, false)
                return CoinViewHolder.PaginationLoading(binding)
            }
            CoinItemType.ERROR.ordinal -> {
                val binding = ItemErrorBinding.inflate(layoutInflater, parent, false)
                return CoinViewHolder.Error(binding, retryCallback)
            }
            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val itemState = differ.currentList[position]
        when (holder) {
            is CoinViewHolder.Title -> holder.bind(itemState as CoinListItem.SectionHeadline)
            is CoinViewHolder.TopRankCrypto -> holder.bind(itemState as CoinListItem.TopRankCrypto)
            is CoinViewHolder.Coin -> holder.bind(itemState as CoinListItem.CryptoCoin)
            is CoinViewHolder.FriendInvite -> holder.bind(itemState as CoinListItem.FriendInvite)
            is CoinViewHolder.PaginationLoading -> {
                // do nothing
            }
            is CoinViewHolder.Error -> {
                // do nothing
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun submitList(coinListItems: List<CoinListItem>) {
        differ.submitList(coinListItems)
    }

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

            private val itemDecoration: HorizontalSpaceItemDecoration by lazy {
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

        class PaginationLoading(
            binding: ItemPaginationLoadingBinding
        ) : CoinViewHolder(binding.root)

        class Error(
            binding: ItemErrorBinding,
            private val retryCallback: () -> Unit
        ) : CoinViewHolder(binding.root) {

            init {
                binding.errorActionTextView.setOnClickListener {
                    retryCallback.invoke()
                }
            }
        }
    }

    class CoinsDiffItemCallback : DiffUtil.ItemCallback<CoinListItem>() {

        override fun areItemsTheSame(oldItem: CoinListItem, newItem: CoinListItem): Boolean {
            return if (oldItem is CoinListItem.CryptoCoin && newItem is CoinListItem.CryptoCoin) {
                oldItem.coin.id == newItem.coin.id
            } else {
                oldItem == newItem
            }
        }

        override fun areContentsTheSame(oldItem: CoinListItem, newItem: CoinListItem): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private val diffItemCallback = CoinsDiffItemCallback()
    }
}
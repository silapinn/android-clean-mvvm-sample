package com.example.cryptocurrency.presentation.coins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.common.widget.decoration.HorizontalMarginMultiItemDecoration
import com.example.cryptocurrency.common.ScrollEndListener
import com.example.cryptocurrency.common.widget.decoration.VerticalSpaceMultiItemDecoration
import com.example.cryptocurrency.databinding.FragmentCoinsBinding
import com.example.cryptocurrency.presentation.coindetail.CoinDetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinsFragment : Fragment() {

    private val viewModel: CoinsViewModel by viewModel()

    private val binding: FragmentCoinsBinding by lazy {
        FragmentCoinsBinding.inflate(layoutInflater)
    }

    private val coinsAdapter: CoinsAdapter by lazy {
        CoinsAdapter(viewModel::onCoinClick, viewModel::retry)
    }

    private val scrollEndListener: ScrollEndListener by lazy {
        ScrollEndListener(viewModel::onScrollEnd)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToViewModel()

        viewModel.loadCoins()
    }


    private fun initView() {
        binding.coinsRecyclerView.apply {
            (layoutManager as? GridLayoutManager)?.spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (coinsAdapter.getItemViewType(position)) {
                            CoinItemType.TOP_RANK_CRYPTO.ordinal,
                            CoinItemType.SECTION_HEADLINE.ordinal,
                            CoinItemType.LOADING.ordinal,
                            CoinItemType.ERROR.ordinal -> 3
                            CoinItemType.COIN.ordinal,
                            CoinItemType.FRIEND_INVITE.ordinal -> 1
                            else -> 3
                        }
                    }
                }

            adapter = coinsAdapter
            addItemDecoration(
                VerticalSpaceMultiItemDecoration(
                    mapOf(
                        CoinItemType.TOP_RANK_CRYPTO.ordinal to resources.getDimensionPixelOffset(
                            R.dimen.item_top_rank_crypto_vertical_space
                        ),
                        CoinItemType.SECTION_HEADLINE.ordinal to resources.getDimensionPixelOffset(
                            R.dimen.item_section_headline_vertical_space
                        ),
                        CoinItemType.COIN.ordinal to resources.getDimensionPixelOffset(
                            R.dimen.item_coin_vertical_space
                        ),
                        CoinItemType.FRIEND_INVITE.ordinal to resources.getDimensionPixelOffset(
                            R.dimen.item_friend_invite_vertical_space
                        )
                    )
                )
            )
            addItemDecoration(
                HorizontalMarginMultiItemDecoration(
                    mapOf(
                        CoinItemType.TOP_RANK_CRYPTO.ordinal to 0,
                        CoinItemType.SECTION_HEADLINE.ordinal to resources.getDimensionPixelOffset(
                            R.dimen.item_section_headline_horizontal_margin
                        ),
                        CoinItemType.COIN.ordinal to resources.getDimensionPixelOffset(
                            R.dimen.item_coin_horizontal_margin
                        ),
                        CoinItemType.FRIEND_INVITE.ordinal to resources.getDimensionPixelOffset(
                            R.dimen.item_friend_invite_horizontal_space
                        )
                    )
                )
            )
            addOnScrollListener(scrollEndListener)
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.loadCoins(forceRefresh = true)
        }
    }

    private fun subscribeToViewModel() {
        viewModel.coinsUiState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is CoinsUiState.Default -> {
                    binding.coinsRecyclerView.post {
                        coinsAdapter.submitList(viewState.listItems)
                    }
                }
                is CoinsUiState.Searching -> {
                    // Don't have enough time
                }
                is CoinsUiState.Loading -> {
                    // Don't have enough time
                }
                is CoinsUiState.Empty -> {
                    // Don't have enough time
                }
            }
        }

        viewModel.isRefreshLoading.observe(viewLifecycleOwner) { isRefreshLoading ->
            binding.refreshLayout.isRefreshing = isRefreshLoading
        }

        viewModel.showCoinDetail.observe(viewLifecycleOwner) { id ->
            val coinDetailFragment = CoinDetailFragment.newInstance(id)
            coinDetailFragment.show(childFragmentManager, null)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoinsFragment()
    }
}
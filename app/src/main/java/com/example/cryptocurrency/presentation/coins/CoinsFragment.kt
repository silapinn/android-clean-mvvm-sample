package com.example.cryptocurrency.presentation.coins

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.common.HorizontalMarginMultiItemDecoration
import com.example.cryptocurrency.common.VerticalSpaceMultiItemDecoration
import com.example.cryptocurrency.databinding.FragmentCoinsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = CoinsFragment()
    }

    private val viewModel: CoinsViewModel by viewModel()

    private val binding: FragmentCoinsBinding by lazy {
        FragmentCoinsBinding.inflate(layoutInflater)
    }

    private val coinsAdapter: CoinsAdapter by lazy {
        CoinsAdapter()
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
            layoutManager = LinearLayoutManager(context)
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
        }
    }

    private fun subscribeToViewModel() {
        viewModel.coinsViewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is CoinsViewState.Default -> {
                    coinsAdapter.coinListItems = viewState.listItems
                    Log.d("CoinsFragment", "${coinsAdapter.coinListItems.size}")
                }
                is CoinsViewState.Searching -> {

                }
                is CoinsViewState.Loading -> {

                }
                is CoinsViewState.Empty -> {

                }
            }
        }
    }
}
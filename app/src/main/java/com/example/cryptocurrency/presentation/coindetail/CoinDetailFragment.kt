package com.example.cryptocurrency.presentation.coindetail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentCoinDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class CoinDetailFragment : BottomSheetDialogFragment() {

    private val viewModel: CoinDetailViewModel by viewModel {
        parametersOf(
            arguments?.getString(ARGUMENT_COIN_ID)
                ?: throw IllegalArgumentException("id must not be null")
        )
    }

    private val binding: FragmentCoinDetailBinding by lazy {
        FragmentCoinDetailBinding.inflate(layoutInflater)
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
        viewModel.loadCoinDetail()
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeToViewModel() {
        viewModel.coinDetail.observe(viewLifecycleOwner) { coinDetail ->
            binding.nameTextView.apply {
                if (coinDetail.color != null) {
                    setTextColor(Color.parseColor(coinDetail.color))
                }
                text = coinDetail.name
            }
            binding.symbolTextView.text = getString(R.string.coin_detail_symbol, coinDetail.symbol)
            binding.priceTextView.text = getString(R.string.coin_detail_formatted_price, coinDetail.price)
            binding.marketCapTextView.text = coinDetail.marketCap.toString()
            binding.detailTextView.apply {
                movementMethod = ScrollingMovementMethod()
                text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(coinDetail.description, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(coinDetail.description)
                }
            }
            binding.iconImageView.load(coinDetail.iconUrl, ImageLoader.Builder(requireContext())
                .components {
                    add(SvgDecoder.Factory())
                }
                .build())
            binding.openWebsiteTextView.setOnClickListener {
                if (coinDetail.websiteUrl != null) {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(coinDetail.websiteUrl))
                    startActivity(browserIntent)
                }
            }
        }
    }

    companion object {
        const val ARGUMENT_COIN_ID = "COIN_ID"

        @JvmStatic
        fun newInstance(id: String) = CoinDetailFragment().apply {
            arguments = bundleOf(ARGUMENT_COIN_ID to id)
        }
    }
}
package com.example.cryptocurrency.presentation.coins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding.coinsRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
//            adapter = coinsAdapter
        }
    }

    private fun subscribeToViewModel() {
        viewModel.coinsViewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is CoinsViewState.Default -> {

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
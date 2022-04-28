package com.example.cryptocurrency.presentation.coins

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptocurrency.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinsFragment : Fragment() {

    private val viewModel: CoinsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coins, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoinsFragment()
    }
}
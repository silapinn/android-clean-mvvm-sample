package com.example.cryptocurrency.presentation.coins

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CoinsAdapter : RecyclerView.Adapter<CoinsAdapter.CoinViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    sealed class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
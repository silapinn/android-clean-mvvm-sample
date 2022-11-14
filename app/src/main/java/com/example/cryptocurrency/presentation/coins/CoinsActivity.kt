package com.example.cryptocurrency.presentation.coins

import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.cryptocurrency.databinding.ActivityCoinsBinding
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.scope.ScopeActivity

class CoinsActivity : ScopeActivity() {

    private val binding by lazy {
        ActivityCoinsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory(scope)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.commit {
            replace<CoinsFragment>(binding.root.id)
        }
    }
}

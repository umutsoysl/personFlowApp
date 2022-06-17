package com.umut.soysal.personapp.feature

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umut.soysal.personapp.core.base.BaseFragment
import com.umut.soysal.personapp.core.extension.safeNavigate
import com.umut.soysal.personapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashScreen: BaseFragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        delay()
        return binding.root
    }

    private fun delay() {
        Handler().postDelayed(Runnable {
            safeNavigate(SplashScreenDirections.actionSplashScreenToHomeFragment())
        }, 2000L)
    }
}
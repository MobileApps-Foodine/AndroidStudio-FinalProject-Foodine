package com.projectakhir.foodine.SignInUp.OnBoarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var pagerAdapter : OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        pagerAdapter = OnBoardingAdapter(supportFragmentManager)
        signinup_onboarding.adapter = pagerAdapter
    }

    private class OnBoardingAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        val NUM_PAGES = 4
        override fun getCount(): Int {
            return NUM_PAGES
        }

        override fun getItem(position: Int): Fragment {
           val onBoard = when(position){
               0 -> OnBoarding1Fragment()
               1 -> OnBoarding2Fragment()
               2 -> OnBoarding3Fragment()
               3 -> OnBoarding4Fragment()
               else -> OnBoarding4Fragment()
           }
            return onBoard
        }

    }
}
package com.projectakhir.foodine.SignInUp.OnBoarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectakhir.foodine.R
import com.projectakhir.foodine.SignInUp.SignActivity
import kotlinx.android.synthetic.main.fragment_on_boarding1.view.*

class OnBoarding1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding1, container, false)
        view.onboard1_skip.setOnClickListener {
            startActivity(Intent(activity, SignActivity::class.java))
        }
        return view
    }
}
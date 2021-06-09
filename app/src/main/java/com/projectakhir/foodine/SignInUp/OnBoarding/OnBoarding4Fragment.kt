package com.projectakhir.foodine.SignInUp.OnBoarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectakhir.foodine.R
import com.projectakhir.foodine.SignInUp.SignActivity
import kotlinx.android.synthetic.main.fragment_on_boarding4.view.*

class OnBoarding4Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding4, container, false)
        view.onboarding4_layout.setOnClickListener {
            val intent = Intent(activity, SignActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
        return view
    }
}
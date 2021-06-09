package com.projectakhir.foodine.MainApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectakhir.foodine.R

class MainShoppinglistFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_shoppinglist, container, false)
        (activity as MainActivity).supportActionBar?.title = "Shoppinglist"
//        (activity as MainActivity).topbarSettings.isVisible = false

        return view
    }
}
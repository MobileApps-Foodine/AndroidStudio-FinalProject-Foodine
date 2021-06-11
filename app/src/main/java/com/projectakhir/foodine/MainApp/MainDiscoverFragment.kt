package com.projectakhir.foodine.MainApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectakhir.foodine.R

class MainDiscoverFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_discover, container, false)
        (activity as MainActivity).supportActionBar?.title = "Discover"
        (activity as MainActivity).drawerLocked()

        return view
    }
}
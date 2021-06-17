package com.projectakhir.foodine.MainApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.fragment_main_profile.*
import kotlinx.android.synthetic.main.fragment_main_profile.view.*

class MainProfileFragment : Fragment() {
    private lateinit var show_name : TextView
    private lateinit var show_bio : TextView
    private lateinit var show_website : TextView
    private lateinit var layoutTestGoals : RelativeLayout
    private lateinit var show_goals_button : ImageView
    private var showGoals : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_profile, container, false)
        (activity as MainActivity).supportActionBar?.title = "Profile"
        (activity as MainActivity).drawerUnlocked()

        show_name = view.mainprofile_name
        show_bio = view.mainprofile_bio
        show_website = view.mainprofile_website
        layoutTestGoals = view.testGoals
        show_goals_button = view.mainprofile_goals_button
        showGoals()

        show_goals_button.setOnClickListener {
            showGoals = !showGoals
            showGoals()
        }

        return view
    }

    private fun showGoals(){
        when(showGoals){
            true ->{
                layoutTestGoals.visibility = View.VISIBLE
                show_goals_button.rotation = 180f
            }
            false -> {
                layoutTestGoals.visibility = View.GONE
                show_goals_button.rotation = 0f
            }
        }
    }
}
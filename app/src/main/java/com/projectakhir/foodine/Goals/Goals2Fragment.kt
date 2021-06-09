package com.projectakhir.foodine.Goals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.fragment_goals2.view.*

class Goals2Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goals2, container, false)
        (activity as GoalsActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as GoalsActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as GoalsActivity).supportActionBar?.title = "Your Current Condition"

        val weightScale = view.goals_profile_weight_scale
        val weightText = view.goals_profile_weight_txt
        val heightScale = view.goals_profile_height_scale
        val heightText = view.goals_profile_height_txt

        //TODO : reduce decimal
        weightText.text = "${setGoal.weight} kg"
        weightScale.setValue(setGoal.weight.toFloat())
        weightScale.setValueListener {
            weightText.text = "$it kg"
            val doubleType = it.toDouble()
            setGoal.weight = it.toDouble()
        }

        heightText.text = "${setGoal.height} cm"
        heightScale.setValue(setGoal.height.toFloat())
        heightScale.setValueListener {
            heightText.text = "$it cm"
            val doubleType = it.toDouble()
            setGoal.height = it.toDouble()
        }
        return view
    }
}
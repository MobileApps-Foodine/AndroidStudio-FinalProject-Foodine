package com.projectakhir.foodine.Goals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.projectakhir.foodine.AllMethod.userDataCondition
import com.projectakhir.foodine.DataClass.UserConditions
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
        if(userDataCondition == null){
            weightText.text = "40.0 kg"
            weightScale.setValue(40.0f)
            heightText.text = "150.0 cm"
            heightScale.setValue(150.0f)
            userDataCondition = UserConditions(40.0f, 150.0f, null)
        }else{
            weightText.text = "${userDataCondition?.userWeight} kg"
            weightScale.setValue((userDataCondition?.userWeight)!!.toFloat())
            heightText.text = "${userDataCondition?.userHeight} cm"
            heightScale.setValue((userDataCondition?.userHeight)!!.toFloat())
        }

        weightScale.setValueListener {
            weightText.text = "$it kg"
            userDataCondition?.userWeight = it
        }

        heightScale.setValueListener {
            heightText.text = "$it cm"
            userDataCondition?.userHeight = it
        }
        return view
    }
}
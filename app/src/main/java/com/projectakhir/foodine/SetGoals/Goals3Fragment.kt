package com.projectakhir.foodine.SetGoals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.fragment_goals3.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Goals3Fragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        (activity as GoalsActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as GoalsActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goals3, container, false)
        val ageText = view.goals_profile_age_editText
        val weightText = view.goals_profile_weight_editText
        val heightText = view.goals_profile_height_editText
        val genderImage = view.goals_profile_gender

        val maleImage = R.drawable.goals_male
        val femaleImage = R.drawable.goals_female

        ageText.setText("${setGoal.age} y.o")
        weightText.setText("${setGoal.weight} kg")
        heightText.setText("${setGoal.height} cm")
        if(setGoal.gender == "male"){
            genderImage.setImageDrawable(resources.getDrawable(maleImage))
        }
        else{
            genderImage.setImageDrawable(resources.getDrawable(femaleImage))
        }

        //TODO : send to database
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Goals3Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
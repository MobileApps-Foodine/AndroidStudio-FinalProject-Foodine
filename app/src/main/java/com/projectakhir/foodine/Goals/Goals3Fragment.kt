package com.projectakhir.foodine.Goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.projectakhir.foodine.AllMethod.defaultProfileImage
import com.projectakhir.foodine.AllMethod.femaleImage
import com.projectakhir.foodine.AllMethod.maleImage
import com.projectakhir.foodine.AllMethod.userDataCondition
import com.projectakhir.foodine.AllMethod.userDataDetail
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.fragment_goals3.view.*

class Goals3Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goals3, container, false)
        (activity as GoalsActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as GoalsActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as GoalsActivity).supportActionBar?.title = "Your Ideal Goals"

        val nameText = view.goals_profile_name
        val ageText = view.goals_profile_age_editText
        val weightText = view.goals_profile_weight_editText
        val heightText = view.goals_profile_height_editText
        val genderImage = view.goals_profile_gender

        //TODO : send API for calculate ideal goals
        genderImage.setImageDrawable(resources.getDrawable(defaultProfileImage))
        nameText.setText("${userDataDetail?.userName}")
        ageText.setText("${(activity as GoalsActivity).userAge} y.o")
        weightText.setText("${userDataCondition?.userWeight} kg")
        heightText.setText("${userDataCondition?.userHeight} cm")
        if(userDataDetail?.userGender == "male"){
            genderImage.setImageDrawable(resources.getDrawable(maleImage))
        }
        else{
            genderImage.setImageDrawable(resources.getDrawable(femaleImage))
        }
        return view
    }
}
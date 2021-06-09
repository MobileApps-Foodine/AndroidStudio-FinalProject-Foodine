package com.projectakhir.foodine.Goals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RelativeLayout
import com.google.android.material.card.MaterialCardView
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.fragment_goals1.view.*
import java.util.*

class Goals1Fragment : Fragment() {
    private fun setDatePicker(datePicker: DatePicker) {
        val today = Calendar.getInstance(TimeZone.getDefault())
        val lastPick = setGoal.dob
        datePicker.init(lastPick.year, lastPick.month, lastPick.date, null)
        datePicker.maxDate = today.timeInMillis
    }

    private fun setGenderMale(
        status: Boolean,
        genderMale: MaterialCardView,
        genderFemale: MaterialCardView,
        dobContent: RelativeLayout
    ){
        when(status){
            true ->{
                genderMale.isChecked = true
                genderFemale.isChecked = false
                setGoal.gender = "male"
            }
            false -> {
                genderFemale.isChecked = true
                genderMale.isChecked = false
                setGoal.gender = "female"
            }
        }
        dobContent.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goals1, container, false)
        (activity as GoalsActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as GoalsActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as GoalsActivity).supportActionBar?.title = "Your Profile"

        val genderMale = view.goals_profile_male
        val genderFemale = view.goals_profile_female
        val dobContent = view.goals_profile_dob
        val datePicker = view.goals_profile_dob_datepicker

        setDatePicker(datePicker)
        dobContent.visibility = View.GONE

        if(setGoal.gender == "male"){
            setGenderMale(true, genderMale, genderFemale, dobContent)
        }
        else if(setGoal.gender == "female"){
            setGenderMale(false, genderMale, genderFemale, dobContent)
        }

        genderMale.setOnClickListener {
            setGenderMale(true, genderMale, genderFemale, dobContent)
        }

        genderFemale.setOnClickListener {
            setGenderMale(false, genderMale, genderFemale, dobContent)
        }

        datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            setGoal.dob = Date(year,monthOfYear,dayOfMonth)
            setGoal.age = Calendar.getInstance().get(Calendar.YEAR) - year
        }
        return view
    }
}
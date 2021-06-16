package com.projectakhir.foodine.Goals

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import com.projectakhir.foodine.AllMethod.Gender
import com.projectakhir.foodine.AllMethod.userData
import com.projectakhir.foodine.AllMethod.userDataDetail
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.fragment_goals1.view.*
import java.text.DateFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Goals1Fragment : Fragment() {
    private var year : Long = 0
    private var month : Long = 0
    private var day : Long = 0

    @SuppressLint("SimpleDateFormat")
    private fun setDatePicker(datePicker: DatePicker) {
        //todo : set init datePicker (start or back) ==2 ke 1, startnya 1900. 3 ke 1 startnya today==
        val today = Calendar.getInstance(TimeZone.getDefault())
        try{
            year = SimpleDateFormat("yyyy").parse(userDataDetail!!.userDob, ParsePosition(0)).time
            month = SimpleDateFormat("mm").parse(userDataDetail!!.userDob, ParsePosition(5)).time
            day = SimpleDateFormat("dd").parse(userDataDetail!!.userDob, ParsePosition(8)).time
        }catch (e:Exception){
            year = today.get(Calendar.YEAR).toLong()
            month = today.get(Calendar.MONTH).toLong()
            day = today.get(Calendar.DAY_OF_MONTH).toLong()
        }

        datePicker.init(year.toInt(), month.toInt(), day.toInt(), null)
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
                userDataDetail?.userGender = Gender.male
            }
            false -> {
                genderFemale.isChecked = true
                genderMale.isChecked = false
                userDataDetail?.userGender = Gender.female
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

        if(userDataDetail?.userGender.toString() == Gender.male.toString()){
            setGenderMale(true, genderMale, genderFemale, dobContent)
        }
        else if(userDataDetail?.userGender.toString() == Gender.female.toString()){
            setGenderMale(false, genderMale, genderFemale, dobContent)
        }

        genderMale.setOnClickListener {
            setGenderMale(true, genderMale, genderFemale, dobContent)
        }

        genderFemale.setOnClickListener {
            setGenderMale(false, genderMale, genderFemale, dobContent)
        }

        datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            userDataDetail?.userDob = "$year-$monthOfYear-$dayOfMonth"
            (activity as GoalsActivity).userAge = Calendar.getInstance().get(Calendar.YEAR) - year
        }
        return view
    }
}
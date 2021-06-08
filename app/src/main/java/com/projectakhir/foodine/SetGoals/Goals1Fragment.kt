package com.projectakhir.foodine.SetGoals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.fragment_goals1.*
import kotlinx.android.synthetic.main.fragment_goals1.view.*
import java.text.SimpleDateFormat
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Goals1Fragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    fun setDatePicker(datePicker: DatePicker) {
        val today = Calendar.getInstance(TimeZone.getDefault())
        datePicker.init(today.get(Calendar.YEAR), Calendar.JANUARY, 1, null)
        datePicker.maxDate = today.timeInMillis
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goals1, container, false)
        val genderMale = view.goals_profile_male
        val genderFemale = view.goals_profile_female
        val dobContent = view.goals_profile_dob
        val datePicker = view.goals_profile_dob_datepicker

        (activity as GoalsActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as GoalsActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        setDatePicker(datePicker)
        dobContent.visibility = View.GONE

        genderMale.setOnClickListener {
            genderMale.isChecked = true
            genderFemale.isChecked = false
            setGoal.gender = "male"
            dobContent.visibility = View.VISIBLE
        }

        genderFemale.setOnClickListener {
            genderFemale.isChecked = true
            genderMale.isChecked = false
            setGoal.gender = "female"
            dobContent.visibility = View.VISIBLE
        }

        datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            setGoal.dob = Date(year,monthOfYear,dayOfMonth)
            setGoal.age = Calendar.getInstance().get(Calendar.YEAR) - year
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Goals1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
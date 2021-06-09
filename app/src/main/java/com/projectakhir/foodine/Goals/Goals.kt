package com.projectakhir.foodine.Goals

import java.util.*

var setGoal : Goals = Goals("","", Date(Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR), Calendar.JANUARY, 1), 0, 40.0, 150.0)
data class Goals(
    var name:String,
    var gender: String,
    var dob: Date,
    var age: Int,
    var weight: Double,
    var height: Double
)

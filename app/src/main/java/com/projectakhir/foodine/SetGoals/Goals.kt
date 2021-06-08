package com.projectakhir.foodine.SetGoals

import java.util.*

var setGoal : Goals = Goals("", Calendar.getInstance().time, 0, 40.0, 150.0)
data class Goals(
    var gender: String,
    var dob: Date,
    var age: Int,
    var weight: Double,
    var height: Double
)

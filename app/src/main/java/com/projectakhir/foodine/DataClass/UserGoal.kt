package com.projectakhir.foodine.DataClass

import com.google.gson.annotations.SerializedName

class UserGoal (
    @SerializedName("bmi_indicator_id") var userBmi: String?,
    @SerializedName("ideal") var userIdeal: String?,
    @SerializedName("calories") var userCalories: String?,
    @SerializedName("nutrient") var userNutrient: String?,
    @SerializedName("user_id") var userId: Int?
)
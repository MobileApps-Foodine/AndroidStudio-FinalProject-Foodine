package com.projectakhir.foodine.DataClass

import com.google.gson.annotations.SerializedName

class UserGoals (
    @SerializedName("bmi_indicator_id") val userBmi: String?,
    @SerializedName("ideal") val userIdeal: String?,
    @SerializedName("calories") val userCalories: String?,
    @SerializedName("nutrient") val userNutrient: String?,
    @SerializedName("user_id") val userId: Int?

)
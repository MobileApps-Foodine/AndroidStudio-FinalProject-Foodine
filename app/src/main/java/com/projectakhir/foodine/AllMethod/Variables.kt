package com.projectakhir.foodine.AllMethod

import android.Manifest
import com.projectakhir.foodine.Goals.setGoal
import com.projectakhir.foodine.R
import com.projectakhir.foodine.DataClass.MainUsers
import com.projectakhir.foodine.DataClass.UserConditions
import com.projectakhir.foodine.DataClass.UserDetail
import com.projectakhir.foodine.DataClass.UserGoal

var userData : MainUsers? = null
var userDataDetail : UserDetail? = null
var userDataCondition : UserConditions? = null
var userGoal : UserGoal? = null
var apiToken : String = ""
val maleImage = R.drawable.goals_male
val femaleImage = R.drawable.goals_female
var defaultProfileImage : Int = if(setGoal.gender == "female"){femaleImage}
                        else{maleImage}
val manifestPermissions : List<String> = listOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.ACCESS_WIFI_STATE,
    Manifest.permission.INTERNET,
    Manifest.permission.CAMERA)

val emptyString = ' '.toString()
val toEmpty = null

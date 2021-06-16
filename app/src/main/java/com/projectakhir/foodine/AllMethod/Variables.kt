package com.projectakhir.foodine.AllMethod

import android.Manifest
import com.projectakhir.foodine.DataClass.MainUsers
import com.projectakhir.foodine.DataClass.UserConditions
import com.projectakhir.foodine.DataClass.UserDetail
import com.projectakhir.foodine.DataClass.UserGoal

var userData : MainUsers? = null
var userDataDetail : UserDetail? = null
var userDataCondition : UserConditions? = null
var userGoal : UserGoal? = null
var apiToken : String = ""

val manifestPermissions : List<String> = listOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.ACCESS_WIFI_STATE,
    Manifest.permission.INTERNET)

val emptyString = ' '.toString()
val toEmpty = null
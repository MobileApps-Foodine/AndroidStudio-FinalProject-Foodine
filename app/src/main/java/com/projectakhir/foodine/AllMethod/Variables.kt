package com.projectakhir.foodine.AllMethod

import android.Manifest
import com.projectakhir.foodine.DataClass.*
import com.projectakhir.foodine.R

var userData : MainUsers? = null
var userDataDetail : UserDetail? = null
var userDataCondition : UserConditions? = null
var userGoal : UserGoal? = null
var localUser : DatabaseModel? = null

val manifestPermissions : List<String> = listOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.ACCESS_WIFI_STATE,
    Manifest.permission.INTERNET,
    Manifest.permission.CAMERA)

val emptyString = ""
val toEmpty = null

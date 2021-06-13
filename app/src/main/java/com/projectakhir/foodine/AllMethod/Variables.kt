package com.projectakhir.foodine.AllMethod

import android.Manifest

var apiToken : String = ""

val manifestPermissions : List<String> = listOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.ACCESS_WIFI_STATE,
    Manifest.permission.INTERNET)

val emptyString = ' '.toString()

//Empty ArrayList
//val emptyCategoriesMerchandise = arrayListOf<CategoryMerchandise>()
//val emptyCategoriesGroup= arrayListOf<CategoryGroup>()
//val emptyMainProduct = arrayListOf<MainProduct>()
//val emptyVersionProduct = arrayListOf<VersionProduct>()
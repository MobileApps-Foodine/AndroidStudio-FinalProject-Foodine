package com.projectakhir.foodine.SettingAPI.ResponseDataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ErrorResponse (
    @SerializedName("message")
    val message : String?,
    @SerializedName("errors")
    val errors : Errors?
){
    @Parcelize
    data class Errors(
    //For User, always add if any validate request in Laravel
        @SerializedName("name") val name : List<String>?,
        @SerializedName("email") val email : List<String>?,
        @SerializedName("password") val password : List<String>?,
        @SerializedName("password_confirmation") val password_confirmation : List<String>?,
        @SerializedName("gender") val gender: List<String>?,
        @SerializedName("date_of_birth") val dob : List<String>?,
        @SerializedName("height") val height : List<String>?,
        @SerializedName("weight") val weight : List<String>?
    ) : Parcelable
}

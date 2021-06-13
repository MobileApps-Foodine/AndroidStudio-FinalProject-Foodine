package com.projectakhir.foodine.SettingAPI.ResponseDataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.projectakhir.foodine.DataClass.UserDetails
import kotlinx.android.parcel.Parcelize


data class SuccessResponse(
    //General Output
    @SerializedName("success") val success : String?,

    @SerializedName("fullname") val fullname : String?,
    @SerializedName("email") val email : String?,
    @SerializedName("password") val password : String?,
    @SerializedName("password_confirmation") val password_confirmation : String?
)
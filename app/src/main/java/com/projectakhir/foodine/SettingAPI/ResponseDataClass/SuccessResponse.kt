package com.projectakhir.foodine.SettingAPI.ResponseDataClass

import com.google.gson.annotations.SerializedName


data class SuccessResponse(
    //General Output
    @SerializedName("success") val success : String?

)
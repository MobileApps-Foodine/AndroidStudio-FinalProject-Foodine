package com.projectakhir.foodine.DataClass

import com.google.gson.annotations.SerializedName
import com.projectakhir.foodine.AllMethod.Gender

class UserDetail (
    @SerializedName("fullname") var userName: String?,
    @SerializedName("gender") var userGender: Gender,
    @SerializedName("photo_profile") var userPhoto: String?,
    @SerializedName("date_of_birth") var userDob: String?, //yyyy-mm-dd
    @SerializedName("bio") var userBio: String?,
    @SerializedName("Url") var userUrl: String?,
    @SerializedName("user_id") var userId: Int?
)


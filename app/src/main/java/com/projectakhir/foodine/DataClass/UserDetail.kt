package com.projectakhir.foodine.DataClass

import com.google.gson.annotations.SerializedName
import com.projectakhir.foodine.AllMethod.emptyString
import com.projectakhir.foodine.AllMethod.toEmpty
import org.w3c.dom.Text
import java.util.*

class UserDetail (
    @SerializedName("fullname") var userName: String?,
    @SerializedName("gender") var userGender: String?,
    @SerializedName("photo_profile") var userPhoto: Text?,
    @SerializedName("date_of_birth") var userDob: String?, //yyyy-mm-dd
    @SerializedName("bio") var userBio: Text?,
    @SerializedName("Url") var userUrl: Text?,
    @SerializedName("user_id") var userId: Int?
)


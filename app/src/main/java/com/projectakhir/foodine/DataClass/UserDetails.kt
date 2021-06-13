package com.projectakhir.foodine.DataClass

import com.google.gson.annotations.SerializedName
import com.projectakhir.foodine.AllMethod.emptyString
import org.w3c.dom.Text
import java.util.*

class UserDetails (

    @SerializedName("fullname") val userName: String?,
    @SerializedName("gender") val userGender: String?,
    @SerializedName("photo_profile") val userPhoto: Text?,
    @SerializedName("date_of_birth") val userDob: Date?,
    @SerializedName("bio") val userBio: Text?,
    @SerializedName("Url") val userUrl: Text?,
    @SerializedName("user_id") val userId: Int?
)
//{
//    constructor(userName: String?, userGender: String?, userPhoto: Text?, userDob: Date?, userBio: Text?, userUrl: Text?, userId: Int?) :
//            this(userName, userGender, userPhoto, userDob, userBio, userUrl, userId)
//}


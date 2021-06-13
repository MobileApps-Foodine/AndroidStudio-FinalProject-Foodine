package com.projectakhir.foodine.DataClass

import com.google.gson.annotations.SerializedName
import com.projectakhir.foodine.AllMethod.emptyString

data class MainUsers(
    @SerializedName("fullname") val userName: String?,
    @SerializedName("email") val userEmail: String?,
    @SerializedName("password") val userPassword: String?,
    @SerializedName("password_confirmation") val userPasswordConfirmation: String?,
    @SerializedName("api_token") val userAPItoken: String?
){
    constructor(userEmail: String?, userPassword: String?) :
            this(emptyString, userEmail, userPassword, emptyString, emptyString)

    constructor(userName: String?, userEmail: String?, userPassword: String?, userPasswordConfirmation: String?) :
            this(userName, userEmail, userPassword, userPasswordConfirmation, emptyString)
}
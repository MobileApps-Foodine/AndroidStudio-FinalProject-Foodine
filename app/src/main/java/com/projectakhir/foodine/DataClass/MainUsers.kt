package com.projectakhir.foodine.DataClass

import com.google.gson.annotations.SerializedName
import com.projectakhir.foodine.AllMethod.toEmpty
import com.projectakhir.foodine.AllMethod.toEmpty

data class MainUsers(
    @SerializedName("fullname") var userNameForRegis: String?,
    @SerializedName("email") var userEmail: String?,
    @SerializedName("password") var userPassword: String?,
    @SerializedName("password_confirmation") var userPasswordConfirmation: String?,
    @SerializedName("api_token") var userAPItoken: String?,
    @SerializedName("user_conditions") var userConditions : ArrayList<UserConditions>?,
    @SerializedName("user_detail") var userDetail : UserDetail?,
    @SerializedName("user_goal") var userGoal : UserGoal?
){
    constructor(userEmail: String?, userPassword: String?) :
            this(toEmpty, userEmail, userPassword, toEmpty, toEmpty, toEmpty, toEmpty, toEmpty)

    constructor(userEmail: String?, userAPItoken: String?, userDetail : UserDetail?) :
            this(toEmpty, userEmail, toEmpty, toEmpty, userAPItoken, toEmpty, userDetail, toEmpty)

    constructor(userName: String?, userEmail: String?, userPassword: String?, userPasswordConfirmation: String?) :
            this(userName, userEmail, userPassword, userPasswordConfirmation, toEmpty, toEmpty, toEmpty, toEmpty)
}
package com.projectakhir.foodine.SettingAPI.Interface

import com.projectakhir.foodine.DataClass.MainUsers
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.SuccessResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Part

interface UserInterface {

        @POST("user/login") // email , password
        fun userLogin(@Body users: MainUsers): Call<MainUsers>

        @POST("user/regis") //name, email, password, password_confirmation
        fun userRegis(@Body users: MainUsers): Call<MainUsers>

        @POST("user/logout")
        fun userLogout() : Call<SuccessResponse>

        @POST("user/addDetailCondition") // age, dob, height, weight
        fun userDetailCondition (@Body data: HashMap<String, String>) : Call<MainUsers>

        @POST ("user/changeEmail")
        fun userChangeEmail (@Body data: HashMap<String, String>): Call<MainUsers>

        @POST ("user/changePassword")
        fun userChangePassword (@Body data: HashMap<String, String>): Call<MainUsers>
    }
package com.projectakhir.foodine.SettingAPI.Interface

import com.projectakhir.foodine.DataClass.MainUsers
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.SuccessResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInterface {

        @POST("user/login") // email , password
        fun userLogin(@Body users: MainUsers): Call<MainUsers>

        @POST("user/regisUser") //name, email, password, password_confirmation
        fun userRegis(@Body users: MainUsers): Call<MainUsers>

        @POST("user/logout")
        fun userLogout(@Body users: MainUsers) : Call<MainUsers>
    }
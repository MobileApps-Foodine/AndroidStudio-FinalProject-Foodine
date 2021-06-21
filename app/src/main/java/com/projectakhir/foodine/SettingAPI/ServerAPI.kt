package com.projectakhir.foodine.SettingAPI

import android.app.Activity
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.GsonBuilder
import com.projectakhir.foodine.AllMethod.localUser
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {
    var BASE_URL : String = "http://foodine.teknisitik.com/api/v1/"
    var retrofit : Retrofit? = null
    var httpClient = OkHttpClient.Builder()
    lateinit var pDialog : SweetAlertDialog


    fun getServerAPI(activity : Activity) : Retrofit?{
        if(retrofit == null){
            httpClient.addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()

                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer ${localUser?.mainUser?.userAPItoken}")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            })

            pDialog = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
            pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
            pDialog.titleText = "Loading"
            pDialog.setCancelable(false)
            pDialog.show()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val client = httpClient.build()
            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }
}
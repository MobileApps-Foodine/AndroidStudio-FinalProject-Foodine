package com.projectakhir.foodine.OtherActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.projectakhir.foodine.AllMethod.*
import com.projectakhir.foodine.DatabaseHandler
import com.projectakhir.foodine.MainApp.MainActivity
import com.projectakhir.foodine.R
import com.projectakhir.foodine.RequestPermission
import com.projectakhir.foodine.SignInUp.OnBoarding.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val timer = object : Thread() {
            override fun run() {
                try {
                    synchronized(this) { sleep(3000) }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    val request = RequestPermission()
                    request.requestAllPermissions(this@SplashScreenActivity)
                }
            }
        }
        timer.start()
    }

    fun splashIsDone(){
        localUser = DatabaseHandler(this).getUser()
        if(!localUser?.mainUser?.userAPItoken.isNullOrEmpty()){
            userData = localUser!!.mainUser
            userDataDetail = localUser!!.mainUser?.userDetail
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            intent.putExtra("from", "SplashScreen")
            startActivity(intent)
        } else{
            startActivity(Intent(this@SplashScreenActivity, OnBoardingActivity::class.java))
        }
        finish()
    }
}
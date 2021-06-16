package com.projectakhir.foodine

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener
import com.projectakhir.foodine.AllMethod.apiToken
import com.projectakhir.foodine.MainApp.MainActivity
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
                    request.requestPermission(this@SplashScreenActivity)
                }
            }
        }
        timer.start()
    }

    fun splashIsDone(){
        //TODO : if apiToken is stated, intent to MainApplication
        if(apiToken.isEmpty()){
            val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
            startActivity(intent)
        } else{
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}
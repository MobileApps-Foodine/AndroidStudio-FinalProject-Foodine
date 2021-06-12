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

//        val permission = PermissionManager()
//        permission.activity = this
//        permission.contentView = findViewById(android.R.id.content)
//        permission.requestAllPermissions()

//        val allListeners = CompositeMultiplePermissionsListener(
//            SnackbarOnAnyDeniedMultiplePermissionsListener
//                .Builder
//                .with(findViewById(android.R.id.content), "All those permissions are needed for this application.")
//                .withOpenSettingsButton("SETTINGS")
//                .build(),
//            object : MultiplePermissionsListener {
//                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
//                    if (report.areAllPermissionsGranted()) {
//                        Toast.makeText(this@SplashScreenActivity, "All permissions are granted!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    permissions: List<PermissionRequest?>?,
//                    token: PermissionToken?
//                ) {
//                    token?.continuePermissionRequest()
//                }
//            }
//        )
//
//        Dexter.withContext(this)
//            .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.ACCESS_NETWORK_STATE,
//                Manifest.permission.ACCESS_WIFI_STATE,
//                Manifest.permission.INTERNET)
//            .withListener(allListeners)
//            .withErrorListener(object : PermissionRequestErrorListener {
//                override fun onError(error: DexterError) {
//                    Log.e("Dexter", "There was an error: $error")
//                }})
//            .check()
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
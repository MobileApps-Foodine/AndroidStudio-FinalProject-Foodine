package com.projectakhir.foodine

import android.R
import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.*
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.projectakhir.foodine.AllMethod.manifestPermissions
import com.projectakhir.foodine.MainApp.SettingsDrawer.SettingsAccountActivity
import com.projectakhir.foodine.OtherActivity.SplashScreenActivity


class RequestPermission(){
    private val errorListener = object : PermissionRequestErrorListener {
        override fun onError(error: DexterError) {
            Log.e("Dexter", "There was an error: $error")
        }}

    fun requestAllPermissions(activity: SplashScreenActivity){
        val allListeners = CompositeMultiplePermissionsListener(
            SnackbarOnAnyDeniedMultiplePermissionsListener
                .Builder
                .with(activity.findViewById(R.id.content), "All those permissions are needed for this application.")
                .withOpenSettingsButton("SETTINGS")
                .withCallback(object : Snackbar.Callback(){
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        activity.splashIsDone()
                    }
                })
                .build(),
            object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        activity.splashIsDone()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }
        )

        Dexter.withContext(activity)
            .withPermissions(manifestPermissions)
            .withListener(allListeners)
            .withErrorListener(errorListener)
            .check()
    }

    fun requestMultiplePermissions(activity: Activity, manifestPermissions : List<String>, title: String){
        val allListeners = CompositeMultiplePermissionsListener(
            SnackbarOnAnyDeniedMultiplePermissionsListener
                .Builder
                .with(activity.findViewById(R.id.content), "$title will not be available until you accept the permission request.")
                .withOpenSettingsButton("SETTINGS")
                .build(),
            object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        if(activity is SettingsAccountActivity){activity.changeImageProfile()}
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }
        )

        Dexter.withContext(activity)
            .withPermissions(manifestPermissions)
            .withListener(allListeners)
            .withErrorListener(errorListener)
            .check()
    }

    fun requestPermission(activity: Activity, manifestPermission: String, message:String){//, @DrawableRes resId: Int){
        Dexter.withContext(activity)
            .withPermission(manifestPermission)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {}

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(activity, "$message will not be available until you accept the permission request.", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener(errorListener)
            .check()
    }
}
package com.projectakhir.foodine

import android.Manifest
import android.R
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener
import com.projectakhir.foodine.AllMethod.manifestPermissions

class RequestPermission(){
    fun requestPermission(activity: SplashScreenActivity){
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
                        Toast.makeText(activity, "All permissions are granted!", Toast.LENGTH_SHORT).show()
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
            .withErrorListener(object : PermissionRequestErrorListener {
                override fun onError(error: DexterError) {
                    Log.e("Dexter", "There was an error: $error")
                }})
            .check()
    }

}
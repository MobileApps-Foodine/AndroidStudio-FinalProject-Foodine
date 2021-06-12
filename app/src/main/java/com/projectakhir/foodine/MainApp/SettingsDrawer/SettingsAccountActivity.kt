package com.projectakhir.foodine.MainApp.SettingsDrawer

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.projectakhir.foodine.AllMethod.warningDiscardChange
import com.projectakhir.foodine.R
import com.projectakhir.foodine.RequestPermission
import com.projectakhir.foodine.SignInUp.SignActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_settings_account.*
import okio.IOException


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class SettingsAccountActivity : AppCompatActivity() {
    private lateinit var menuNavigation : NavigationView
    private lateinit var initName : String
    private lateinit var initWebsite : String
    private lateinit var initBio : String
    private val request = RequestPermission()
    private val imageGalleryCode = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_account)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = "Account"

        menuNavigation = findViewById(R.id.account_menu_navigation)
        menuNavigation.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.account_email -> {
                    true }
                R.id.account_password ->{ true }
                R.id.account_delete -> {
                    deleteAccount()
                    true
                }
                else -> false
            }
        }

        // TODO: set init value (image)
        initName = account_edit_name_txt.text.toString()
        initBio = account_edit_bio_txt.text.toString()
        initWebsite = account_edit_website_txt.text.toString()

        //TODO : setonclicklistener for edit image
        account_edit_image.setOnClickListener {
            val items = arrayOf("Open camera", "Select photo", "Delete profile photo")
            MaterialAlertDialogBuilder(this)
                .setItems(items) { dialog, which ->
                    when(which){
                        0 -> Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show()
                        1 -> request.requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, "Storage Permission", "Selecting photo")
                        2 -> Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
                    }
                }
                .show()
        }

        account_image.setOnClickListener { account_edit_image.performClick() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == imageGalleryCode && data!=null){
            CropImage.activity(data.data)
                .setActivityTitle("Edit Photo")
                .setAspectRatio(1,1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAutoZoomEnabled(true)
                .setAllowFlipping(false)
                .start(this)
        }
        if (requestCode === CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode === RESULT_OK) {
                val resultUri: Uri = result.uri
                try{
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, resultUri)
                    account_image.setImageBitmap(bitmap)
                }catch (e:IOException){
                    e.printStackTrace()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(0, 0, 0, "Save")
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            0 -> {
                // TODO: send to database
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onSupportNavigateUp(): Boolean {
        // TODO: check if any edittext data changeable
        if(account_edit_name_txt.text.toString() != initName ||
            account_edit_bio_txt.text.toString() != initBio ||
            account_edit_website_txt.text.toString() != initWebsite){
            warningDiscardChange(this)
        }
        else{
            onBackPressed()
        }
        return true
    }

    fun deleteAccount(){
        val alertDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        alertDialog.setTitleText("Delete Account")
            .setContentText("Are you sure want to delete this account? All of your data and information will be deleted")
            .setConfirmText("Confirm")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, SignActivity::class.java))
            }
            .setCancelButton(
                "Cancel"
            ) { it.dismissWithAnimation() }
            .show()

        val cancelBtn = alertDialog.findViewById<Button>(R.id.cancel_button)
        cancelBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_black))
        cancelBtn.setTextColor(ContextCompat.getColor(this, R.color.prim_green))
    }

    fun selectPhoto() {
        val image = Intent(Intent.ACTION_PICK)
        image.type = "image/*"
        startActivityForResult(image, imageGalleryCode)
    }
}
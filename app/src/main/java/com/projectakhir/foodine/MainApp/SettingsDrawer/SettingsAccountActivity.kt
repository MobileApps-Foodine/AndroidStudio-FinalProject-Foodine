package com.projectakhir.foodine.MainApp.SettingsDrawer

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.projectakhir.foodine.AllMethod.defaultProfileImage
import com.projectakhir.foodine.AllMethod.warningDiscardChange
import com.projectakhir.foodine.Goals.setGoal
import com.projectakhir.foodine.OtherActivity.ShowPictureActivity
import com.projectakhir.foodine.R
import com.projectakhir.foodine.RequestPermission
import com.projectakhir.foodine.SignInUp.SignActivity
import kotlinx.android.synthetic.main.activity_settings_account.*
import java.io.*
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


class SettingsAccountActivity : AppCompatActivity(){
    private lateinit var menuNavigation : NavigationView
    private lateinit var initName : String
    private lateinit var initWebsite : String
    private lateinit var initBio : String
    private lateinit var initGender : String
    private lateinit var initDOB : String
    private var initImage : Bitmap? = null
    private var currentImage : Bitmap? = null
    private val request = RequestPermission()
    lateinit var changePassword : BottomSheetDialogFragment
    lateinit var changeEmail : BottomSheetDialogFragment

    //TODO : change email
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_account)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = "Account"
        initialDetailProfile()
        genderConfiguration()
        imageConfiguration()
        dobConfiguration()

        menuNavigation = findViewById(R.id.account_menu_navigation)
        menuNavigation.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.account_email -> {
                    changeEmail = ChangeEmailFragment()
                    changeEmail.show(supportFragmentManager, "changeEmailSheet")
                    true }
                R.id.account_password ->{
                    changePassword = ChangePasswordFragment()
                    changePassword.show(supportFragmentManager, "changePasswordSheet")
                    true }
                R.id.account_delete -> {
                    deleteAccount()
                    true
                }
                else -> false
            }
        }


    }

    private fun initialDetailProfile() {
        // TODO: get init value form database
        initName = setGoal.name
        initBio = account_edit_bio_txt.text.toString()
        initWebsite = account_edit_website_txt.text.toString()
        initGender = setGoal.gender
        initDOB = account_edit_dob_txt.text.toString()
        //TODO : image -> check if image in database is null or not. if null, will set as default. Now set as a bitmap

        account_edit_name_txt.setText(initName)
        account_edit_bio_txt.setText(initBio)
        account_edit_website_txt.setText(initWebsite)
        val imageBitmap = if(initImage == null){
            BitmapFactory.decodeResource(this.getResources(), defaultProfileImage)
        }
        else{
            MediaStore.Images.Media.getBitmap(contentResolver, (Uri.parse(initImage.toString())))
        }
        account_image.setImageBitmap(imageBitmap)
        initImage = imageBitmap
        currentImage = imageBitmap
    }

    private fun dobConfiguration() {
        account_edit_dob_txt.setOnClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("WIB"))

            val pos = ParsePosition(0)
            val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.US)
//            val lastDate = sdf.parse(account_edit_dob_txt.text.toString(), pos).time

            calendar[Calendar.MONTH] = Calendar.DECEMBER
            val decThisYear = calendar.timeInMillis
            val constraintBuilder = CalendarConstraints.Builder()
            constraintBuilder.setValidator(DateValidatorPointBackward.now()).setEnd(decThisYear)

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Date of Birth")
//                .setSelection(lastDate + TimeUnit.DAYS.toMillis(1))
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setCalendarConstraints(constraintBuilder.build())
                .setTheme(R.style.MyDatePicker)
                .build()
            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener{
                val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.US)
                account_edit_dob_txt.setText(sdf.format(datePicker.selection))}
            datePicker.addOnNegativeButtonClickListener{}
            datePicker.isCancelable = false
        }
    }

    private fun imageConfiguration() {
        account_edit_image.setOnClickListener {
            val items = arrayOf("Select photo", "Delete profile picture")
            MaterialAlertDialogBuilder(this)
                .setItems(items) { dialog, which ->
                    when(which){
                        0 -> request.requestMultiplePermissions(this, listOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), "Change profile picture")
                        1 -> {
                            account_image.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), defaultProfileImage))
                            currentImage = null
                        }
                    }
                }
                .show()
        }

        account_image.setOnClickListener {
            try {
                val filename = "bitmap.png"
                val stream = openFileOutput(filename, MODE_PRIVATE)
                currentImage!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.close()
                val intent = Intent(this, ShowPictureActivity::class.java)
                intent.putExtra("actionBarTitle", account_edit_name_txt.text.toString())
                intent.putExtra("photoContent", filename)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        account_image.setOnLongClickListener { account_edit_image.performClick() }
    }

    private fun genderConfiguration() {
        account_gender_group.onPositionChangedListener =
            SegmentedButtonGroup.OnPositionChangedListener { position ->
                //TODO : change image default if image didn't set
            }
        account_gender_group.setPosition(1,false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        Toast.makeText(this, "$requestCode + $resultCode + ${data.toString()}", Toast.LENGTH_LONG).show()
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ->{
                    val result = CropImage.getActivityResult(data)
                    val resultUri: Uri = result?.uriContent!!
                    try{
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, resultUri)
                        account_image.setImageBitmap(bitmap)
                        currentImage = bitmap//getImageUriFromBitmap(this, bitmap).toString()
                    }catch (e:IOException) {
                        e.printStackTrace()
                    }
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
                Toast.makeText(this, "send database", Toast.LENGTH_SHORT).show()
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
        if(account_edit_name_txt.text.toString() != initName ||
            account_edit_bio_txt.text.toString() != initBio ||
            account_edit_website_txt.text.toString() != initWebsite ||
            currentImage != initImage){
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

    fun changeImageProfile(){
        CropImage.activity()
            .setActivityTitle("Edit Photo")
            .setAspectRatio(1,1)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAutoZoomEnabled(true)
            .setAllowFlipping(false)
            .start(this@SettingsAccountActivity)
    }
}
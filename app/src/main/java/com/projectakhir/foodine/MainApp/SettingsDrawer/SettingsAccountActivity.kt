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
import com.projectakhir.foodine.AllMethod.*
import com.projectakhir.foodine.OtherActivity.ShowPictureActivity
import com.projectakhir.foodine.R
import com.projectakhir.foodine.RequestPermission
import com.projectakhir.foodine.SignInUp.SignActivity
import kotlinx.android.synthetic.main.activity_settings_account.*
import java.io.*
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SettingsAccountActivity : AppCompatActivity(){
    private lateinit var initPhoto: String
    private lateinit var menuNavigation : NavigationView
    private lateinit var initName : String
    private lateinit var initWebsite : String
    private lateinit var initBio : String
    private lateinit var initGender : String
    private lateinit var initDOB : String
    private lateinit var currentGender : String
    private lateinit var currentDOB: String
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
        // TODO: initPhoto + initImage (must resolve data type of photo profile)
        initName = if(userDataDetail?.userName != null){ userDataDetail?.userName!! }
                    else { emptyString }
        initBio = if(userDataDetail?.userBio != null){ userDataDetail?.userBio!! }
                    else{ emptyString }
        initWebsite = if(userDataDetail?.userUrl != null) { userDataDetail?.userUrl!! }
                    else{ emptyString }
        initGender = if(userDataDetail?.userGender != null){ userDataDetail?.userGender!!.toString() }
                    else{ "male" }
        initPhoto = if(userDataDetail?.userPhoto != null){ userDataDetail?.userPhoto!! }
                    else { emptyString }

        account_edit_name_txt.setText(initName)
        account_edit_bio_txt.setText(initBio)
        account_edit_website_txt.setText(initWebsite)
        val imageBitmap = if(initImage == null || initPhoto == emptyString){
            BitmapFactory.decodeResource(this.getResources(),
                Gender.valueOf(userDataDetail?.userGender!!.toString()).getImageDefault())
        }
        else{
            MediaStore.Images.Media.getBitmap(contentResolver, (Uri.parse(initImage.toString())))
        }
        account_image.setImageBitmap(imageBitmap)
        initImage = imageBitmap
        currentImage = initImage
        currentGender = initGender
    }

    private fun showDOB(data : Long?) {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.US)
        account_edit_dob_txt.setText(sdf.format(data))
    }

    private fun dobConfiguration() {
        val pos = ParsePosition(0)
        val sdf = SimpleDateFormat("yyyy-mm-dd", Locale.US)
        val lastDate = sdf.parse(userDataDetail!!.userDob, pos).time
        showDOB(lastDate)

        initDOB = lastDate.toString()
        currentDOB = initDOB

        account_edit_dob_txt.setOnClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("WIB"))
            calendar[Calendar.MONTH] = Calendar.DECEMBER
            val constraintBuilder = CalendarConstraints.Builder()
            constraintBuilder.setValidator(DateValidatorPointBackward.now()).setEnd(calendar.timeInMillis)

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Date of Birth")
                .setSelection(lastDate + TimeUnit.DAYS.toMillis(1))
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setCalendarConstraints(constraintBuilder.build())
                .setTheme(R.style.MyDatePicker)
                .build()
            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener{
                showDOB(datePicker.selection)
                currentDOB = sdf.format(datePicker.selection)
            }
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
                            currentImage = BitmapFactory.decodeResource(this.getResources(),
                                Gender.valueOf(currentGender).getImageDefault())
                            account_image.setImageBitmap(currentImage)
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
                currentGender = when(position){
                    0 -> Gender.male.toString()
                    else -> Gender.female.toString()
                }
                if(initImage == null || initPhoto == emptyString){
                    account_image.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),
                        Gender.valueOf(currentGender).getImageDefault()))
                }
            }

        account_gender_group.setPosition(
            when(userDataDetail?.userGender!!)
            {
                Gender.male -> 0
                else -> 1
            },false)
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
                // TODO: send to database : currentGender, currentDOB, currentImage (remain use their edittext.text)
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
            currentImage != initImage ||
            currentDOB != initDOB ||
            currentGender != initGender){
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
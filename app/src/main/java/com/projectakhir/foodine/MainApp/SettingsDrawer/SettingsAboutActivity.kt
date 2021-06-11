package com.projectakhir.foodine.MainApp.SettingsDrawer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.projectakhir.foodine.BuildConfig
import com.projectakhir.foodine.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.security.AccessController.getContext


class SettingsAboutActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        TODO : logo round
        val aboutPage= AboutPage(this)
            .isRTL(false)
            .setImage(R.drawable.logo_drawable)
            .setDescription(getString(R.string.app_name))
            .addGroup(getString(R.string.app_desc))
            .addItem(Element("Version " + BuildConfig.VERSION_NAME, R.drawable.ic_about_fill))
            .addGitHub("MobileApps-Foodine")

        setContentView(aboutPage.create())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = "About"
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
        onBackPressed()
        return true
    }
}
package com.projectakhir.foodine.MainApp.ProfileMenu.SettingsDrawer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatRatingBar
import com.projectakhir.foodine.AllMethod.warningDiscardChange
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.activity_settings_feedback.*

class SettingsFeedbackActivity : AppCompatActivity() {
    private var ratingValue : Float? = null
    private lateinit var ratingBar : AppCompatRatingBar

    fun setRating(value:Float){
        ratingValue = value
        settings_feedback_ratingtxt.text =
            (when (ratingValue!!) {
                in 0f..1f -> "Very Bad"
                in 1.0f..2f -> "Bad"
                in 2.0f..3f -> "Good"
                in 3.0f..4f -> "Great"
                else -> "Excellent"
            }).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_feedback)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = "Feedback"
        setRating(5f)
        settings_feeedback_ratingbar.rating = 5f

        ratingBar = findViewById(R.id.settings_feeedback_ratingbar)
        ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                setRating(rating)
            }

        settings_feedback_send.setOnClickListener {
            //TODO : send to database

            Toast.makeText(this, "Your feedback has been sent", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
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
        if(settings_feedback_content.text.isNullOrEmpty()){
            onBackPressed()
        }
        else{
            warningDiscardChange(this)
        }
        return true
    }
}
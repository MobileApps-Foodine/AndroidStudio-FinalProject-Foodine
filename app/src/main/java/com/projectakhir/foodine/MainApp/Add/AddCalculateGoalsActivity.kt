package com.projectakhir.foodine.MainApp.Add

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.activity_add_calculate_goals.*
import kotlinx.android.synthetic.main.activity_add_shoppinglist.*
import kotlinx.android.synthetic.main.activity_add_shoppinglist.add_shoppinglist_topbar_menu

class AddCalculateGoalsActivity : AppCompatActivity() {
    private var sendData : Boolean?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_calculate_goals)

        setSupportActionBar(add_calculate_goals_topbar_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = "Calculate Goals"

        sendData = intent.getBooleanExtra("sendData", false)
        //TODO : will send data (putBooleanExtra as identifier will send data or not)

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
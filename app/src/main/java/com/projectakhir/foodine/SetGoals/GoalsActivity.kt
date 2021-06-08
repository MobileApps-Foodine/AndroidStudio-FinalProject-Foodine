package com.projectakhir.foodine.SetGoals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.projectakhir.foodine.MainApp.MainActivity
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.activity_goals.nav_host_fragment
import kotlinx.android.synthetic.main.fragment_goals1.*
import java.text.SimpleDateFormat


class GoalsActivity : AppCompatActivity() {
    private fun showNextButton(state:Boolean){
        goals_next_button.visibility = when(state){
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    fun showContentFragment(){
        var content = when(nav_host_fragment.findNavController().currentDestination!!.id){
            R.id.goals1Fragment -> findViewById<RelativeLayout>(R.id.goals_profile_dob)
            R.id.goals2Fragment -> findViewById<RelativeLayout>(R.id.goals_profile_height)
            else -> null
        }

        if(content?.visibility == View.VISIBLE){
            showNextButton(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

        setSupportActionBar(goals_topbar_menu)
        showNextButton(false)
        goals_next_button.setOnClickListener {
            when(nav_host_fragment.findNavController().currentDestination!!.id){
                R.id.goals1Fragment -> {
                    nav_host_fragment.findNavController().navigate(R.id.action_goals1Fragment_to_goals2Fragment)
                    goals_step_view.go(1,true)
                    showNextButton(false)
                }
                R.id.goals2Fragment -> {
                    nav_host_fragment.findNavController().navigate(R.id.action_goals2Fragment_to_goals3Fragment)
                    goals_step_view.go(2, true)
                    goals_next_button.text = "DONE"
                }
                R.id.goals3Fragment -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        showContentFragment()
        return super.dispatchTouchEvent(ev)
    }

    override fun onSupportNavigateUp(): Boolean {
        goals_step_view.go(goals_step_view.currentStep-1, true)
        onBackPressed()
        return true
    }
}
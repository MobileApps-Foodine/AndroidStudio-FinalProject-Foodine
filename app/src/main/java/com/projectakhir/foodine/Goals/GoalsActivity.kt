package com.projectakhir.foodine.Goals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.projectakhir.foodine.AllMethod.userData
import com.projectakhir.foodine.AllMethod.userDataCondition
import com.projectakhir.foodine.AllMethod.userDataDetail
import com.projectakhir.foodine.DataClass.MainUsers
import com.projectakhir.foodine.MainApp.MainActivity
import com.projectakhir.foodine.R
import com.projectakhir.foodine.SettingAPI.Interface.UserInterface
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.ErrorHelper
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.ErrorResponse
import com.projectakhir.foodine.SettingAPI.ServerAPI
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.activity_goals.nav_host_fragment
import kotlinx.android.synthetic.main.fragment_goals1.*
import kotlinx.android.synthetic.main.fragment_goals2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class GoalsActivity : AppCompatActivity() {
    var userAge : Int = 0

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
        supportActionBar?.title = "Your Profile"

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
                    goals_next_button.text = "Done"
                }
                R.id.goals3Fragment -> {
                    val mainUser = hashMapOf(
                        "gender" to userDataDetail!!.userGender!!,
                        "date_of_birth" to userDataDetail!!.userDob.toString(),
                        "weight" to userDataCondition!!.userWeight.toString(),
                        "height" to userDataCondition!!.userHeight.toString())

                    val userInterface : UserInterface = ServerAPI().getServerAPI()!!.create(UserInterface::class.java)
                    userInterface.userDetailCondition(mainUser).enqueue(object : Callback<MainUsers>{
                        override fun onResponse(call: Call<MainUsers>, response: Response<MainUsers>) {
                            if (response!!.isSuccessful) {
                                userData = response.body()
                                userDataDetail = userData!!.userDetail
                                userDataCondition = userData?.userConditions!!.size.minus(
                                    1
                                ).let { it1 -> userData!!.userConditions!!.get(it1) }
                                Toast.makeText(this@GoalsActivity, "Register Successful", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@GoalsActivity, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                try {
                                    val output: ErrorResponse = ErrorHelper().parseErrorBody(response)
                                    Toast.makeText(this@GoalsActivity, output.toString(), Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {}
                            }
                        }

                        override fun onFailure(call: Call<MainUsers>, t: Throwable) {
                            Toast.makeText(this@GoalsActivity, t.toString(), Toast.LENGTH_SHORT).show()
                            Log.d("failure", t.toString())
                        }

                    })
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
        goals_next_button.text = "Next"
        onBackPressed()
        return true
    }
}
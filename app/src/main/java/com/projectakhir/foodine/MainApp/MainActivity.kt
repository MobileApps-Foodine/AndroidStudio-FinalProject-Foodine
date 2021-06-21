package com.projectakhir.foodine.MainApp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.projectakhir.foodine.AllMethod.*
import com.projectakhir.foodine.BuildConfig
import com.projectakhir.foodine.DataClass.DatabaseModel
import com.projectakhir.foodine.DatabaseHandler
import com.projectakhir.foodine.MainApp.AddMenu.AddCalculateGoalsActivity
import com.projectakhir.foodine.MainApp.ProfileMenu.SettingsDrawer.*
import com.projectakhir.foodine.R
import com.projectakhir.foodine.SettingAPI.Interface.UserInterface
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.ErrorHelper
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.ErrorResponse
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.SuccessResponse
import com.projectakhir.foodine.SettingAPI.ServerAPI
import com.projectakhir.foodine.SignInUp.SignActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), DrawerInterface{
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var actionBarToggle : ActionBarDrawerToggle
    private lateinit var bottomBar : BottomNavigationView
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var drawerNavigation : NavigationView
    private lateinit var topBarSettings : MenuItem
    var drawerLock : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //API
        val msg = intent.getStringExtra("from")
        when(msg){
            "SplashScreen" -> {
                //todo : get data from database (userCondition)
            }
        }

        //Bottom Bar
        bottomBar = findViewById(R.id.main_bottombar_menu)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomBar.setupWithNavController(navController)

        //Drawer
        drawerLayout = findViewById(R.id.main_drawer_layout)
        drawerNavigation = findViewById(R.id.main_drawer_navigation)
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0,0)
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        drawerNavigation.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.settings_update_goals -> {
                    val intent = Intent(this, AddCalculateGoalsActivity::class.java)
                    intent.putExtra("sendData", true)
                    startActivity(intent)
                    true
                }
                R.id.settings_account-> {
                    startActivity(Intent(this, SettingsAccountActivity::class.java))
                    true
                }
                R.id.settings_feedback -> {
                    startActivity(Intent(this, SettingsFeedbackActivity::class.java))
                    true
                }
                R.id.settings_about -> {
                    startActivity(Intent(this, SettingsAboutActivity::class.java))
                    true
                }
                R.id.setting_logout -> {
                    logoutDialog()
                    true
                }
                else -> {
                    false
                }
            }
        }

//        val logoutItem = drawerNavigation.menu.findItem(R.id.setting_logout)
//        val logoutTitle = SpannableString(logoutItem.title)
//        logoutTitle.setSpan(TextAppearanceSpan(this, R.style.LogoutTextAppearance), 0, logoutTitle.length, 0)
//        logoutItem.title = logoutTitle

        val versionText = findViewById<TextView>(R.id.settings_version)
        versionText.text = BuildConfig.VERSION_NAME
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.topbar_settings -> {
                if(this.drawerLayout.isDrawerOpen(GravityCompat.END)){
                    this.drawerLayout.closeDrawer(GravityCompat.END)
                }
                else{
                    this.drawerLayout.openDrawer(GravityCompat.END)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_topbar_main, menu)
        topBarSettings = menu.findItem(R.id.topbar_settings)
        topBarSettings.isVisible = drawerLock != true
        return true
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

    fun logoutDialog(){
        val alertDialog = SweetAlertDialog(this@MainActivity, SweetAlertDialog.WARNING_TYPE)
        alertDialog.setContentText("Are you sure want to log out?")
            .setConfirmText("Logout")
            .setConfirmClickListener {
                val serverAPI = ServerAPI()
                val userInterface : UserInterface = serverAPI.getServerAPI(this)!!.create(UserInterface::class.java)
                userInterface.userLogout().enqueue(object : Callback<SuccessResponse> {
                    override fun onResponse(
                        call: Call<SuccessResponse>,
                        response: Response<SuccessResponse>
                    ) {
                        if (response!!.isSuccessful) {
                            serverAPI.pDialog.dismissWithAnimation()
                            userData = toEmpty
                            userDataDetail = toEmpty
                            userDataCondition = toEmpty
                            it.dismissWithAnimation()
                            localUser = DatabaseHandler(this@MainActivity).deleteUser()
                            Toast.makeText(this@MainActivity, "Logged out", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MainActivity, SignActivity::class.java)
                            intent.putExtra("from", "MainActivity")
                            startActivity(intent)
                        } else {
                            serverAPI.pDialog.dismissWithAnimation()
                            try {
                                val output : ErrorResponse = ErrorHelper().parseErrorBody(response)
                                Toast.makeText(this@MainActivity, output.toString(), Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {}
                        }
                    }

                    override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                        failedDialog(serverAPI.pDialog)
                        Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("failure", t.toString())
                    }

                })

            }
            .setCancelButton(
                "Cancel"
            ) { it.dismissWithAnimation() }
            .show()

        val cancelBtn = alertDialog.findViewById<Button>(R.id.cancel_button)
        cancelBtn.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.transparent_black))
        cancelBtn.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.prim_green))
    }

    override fun drawerLocked() {
        drawerLock = true
        invalidateOptionsMenu()
    }

    override fun drawerUnlocked() {
        drawerLock = false
        invalidateOptionsMenu()
    }
}
package com.projectakhir.foodine.MainApp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.projectakhir.foodine.MainApp.Add.AddCalculateGoalsActivity
import com.projectakhir.foodine.R
import com.projectakhir.foodine.SignInUp.SignActivity
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var actionBarToggle : ActionBarDrawerToggle
    lateinit var topbarSettings : MenuItem

    fun isTopBarSettingsInitialized():Boolean{
        return this::topbarSettings.isInitialized
    }

//    override fun onStart() {
//        super.onStart()
//        topbarSettings.isVisible = when(main_nav_host_fragment.findNavController().currentDestination!!.id){
//            R.id.mainProfileFragment -> true
//            else -> false
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Top Bar
        setSupportActionBar(main_topbar_menu)
        topbarSettings = main_topbar_menu.menu.findItem(R.id.topbar_settings)
        topbarSettings.isEnabled = false

        //Bottom Bar
        val navController = findNavController(R.id.main_nav_host_fragment)
        main_bottombar_menu.setupWithNavController(navController)

        //Drawer
        actionBarToggle = ActionBarDrawerToggle(this, main_drawer_layout, 0,0)
        main_drawer_layout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        main_drawer_navigation.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.settings_update_goals -> {
                    val intent = Intent(this, AddCalculateGoalsActivity::class.java)
                    intent.putExtra("sendData", true)
                    startActivity(intent)
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_topbar_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.topbar_settings -> { //TODO : hide settings except in Profile
                if(this.main_drawer_layout.isDrawerOpen(GravityCompat.END)){
                    this.main_drawer_layout.closeDrawer(GravityCompat.END)
                }
                else{
                    this.main_drawer_layout.openDrawer(GravityCompat.END)
                }
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
        onBackPressed()
        return true
    }

    fun logoutDialog(){
        //TODO : correct dialog
        val builder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure?")
        builder.setCancelable(false)

        builder.setPositiveButton("Yes"){ dialog: DialogInterface, which ->
            //TODO : send api then delete sqlite
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            startActivity(Intent(this, SignActivity::class.java))
        }

        builder.setNegativeButton("No"){dialog:DialogInterface, which ->
            dialog.dismiss()
        }

        builder.show()
    }
}
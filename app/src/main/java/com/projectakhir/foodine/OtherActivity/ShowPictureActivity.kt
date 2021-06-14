package com.projectakhir.foodine.OtherActivity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.activity_show_picture.*
import java.io.FileInputStream


class ShowPictureActivity : AppCompatActivity() {
    private var currentImage : Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_picture)

        val titleBar = intent.getStringExtra("actionBarTitle")

        window.statusBarColor = Color.BLACK
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.black)))
        supportActionBar?.title = if(titleBar.isNullOrEmpty()){"Profile Picture"}
        else{titleBar}

        val filename = intent.getStringExtra("photoContent")
        try {
            val `is`: FileInputStream = openFileInput(filename)
            currentImage = BitmapFactory.decodeStream(`is`)
            `is`.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        show_picture.setImageBitmap(currentImage)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
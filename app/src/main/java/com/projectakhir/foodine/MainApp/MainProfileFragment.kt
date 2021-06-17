package com.projectakhir.foodine.MainApp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.tabs.TabLayout
import com.projectakhir.foodine.AllMethod.Gender
import com.projectakhir.foodine.AllMethod.capitalizeWords
import com.projectakhir.foodine.AllMethod.emptyString
import com.projectakhir.foodine.AllMethod.userDataDetail
import com.projectakhir.foodine.MainApp.ProfileMenu.ProfileDashboardFragment
import com.projectakhir.foodine.MainApp.ProfileMenu.ProfileFeedsFragment
import com.projectakhir.foodine.MainApp.ProfileMenu.ProfileWishlistFragment
import com.projectakhir.foodine.MainApp.ProfileMenu.ViewPagerAdapter
import com.projectakhir.foodine.OtherActivity.ShowPictureActivity
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.activity_settings_account.*
import kotlinx.android.synthetic.main.fragment_main_profile.view.*
import java.io.FileOutputStream

class MainProfileFragment : Fragment() {
    private lateinit var show_name : TextView
    private lateinit var show_bio : TextView
    private lateinit var show_website : TextView
    private lateinit var show_image : ShapeableImageView
    private var imageBitmap : Bitmap? = null
    private lateinit var layoutTestGoals : RelativeLayout
    private lateinit var show_goals_button : ImageView
    private lateinit var viewPager : ViewPager
    private lateinit var tabs : TabLayout
    private var showGoals : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_profile, container, false)
        (activity as MainActivity).supportActionBar?.title = "Profile"
        (activity as MainActivity).drawerUnlocked()

        show_name = view.mainprofile_name
        show_bio = view.mainprofile_bio
        show_website = view.mainprofile_website
        show_image = view.mainprofile_image
        layoutTestGoals = view.testGoals
        show_goals_button = view.mainprofile_goals_button
        viewPager = view.mainprofile_view_pager
        tabs = view.mainprofile_tabs

        showData()
        showGoals()
        setUpTabs()

        show_goals_button.setOnClickListener {
            showGoals = !showGoals
            showGoals()
        }

        show_image.setOnClickListener {
            try {
                val filename = "bitmap.png"
                val stream = (activity as MainActivity).openFileOutput(filename, AppCompatActivity.MODE_PRIVATE)
                imageBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.close()
                val intent = Intent(activity, ShowPictureActivity::class.java)
                intent.putExtra("actionBarTitle", userDataDetail?.userName.toString())
                intent.putExtra("photoContent", filename)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return view
    }

    private fun showData() {
        if(userDataDetail?.userName != null){
            show_name.text = userDataDetail?.userName!!.capitalizeWords()
        } else {
            show_name.visibility  = View.GONE
        }
        if(userDataDetail?.userBio != null){
            show_bio.text = userDataDetail?.userBio!!
        } else{
            show_bio.visibility = View.GONE
        }
        if(userDataDetail?.userUrl != null) {
            show_website.text = userDataDetail?.userUrl!!
        } else{
            show_website.visibility = View.GONE
        }
        imageBitmap = if(!userDataDetail?.userPhoto.isNullOrEmpty()){
            //TODO : set as user photo
        //MediaStore.Images.Media.getBitmap(contentResolver, (Uri.parse(initImage.toString())))
            null
        }
        else{
            BitmapFactory.decodeResource(this.getResources(), Gender.valueOf(userDataDetail?.userGender!!.toString()).getImageDefault())
        }
        show_image.setImageBitmap(imageBitmap)
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ProfileDashboardFragment(), "Dashboard")
        adapter.addFragment(ProfileFeedsFragment(), "Feeds")
        adapter.addFragment(ProfileWishlistFragment(), "Wishlist")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_dashboard)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_feeds)
        tabs.getTabAt(2)!!.setIcon(R.drawable.ic_bookmark)
    }

    private fun showGoals(){
        when(showGoals){
            true ->{
                layoutTestGoals.visibility = View.VISIBLE
                show_goals_button.rotation = 180f
            }
            false -> {
                layoutTestGoals.visibility = View.GONE
                show_goals_button.rotation = 0f
            }
        }
    }
}
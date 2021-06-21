package com.projectakhir.foodine.MainApp.AddMenu

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.projectakhir.foodine.DataClass.ShoppinglistItem
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.activity_add_shoppinglist.*
import kotlinx.android.synthetic.main.fragment_main_add.*
import okhttp3.internal.notifyAll
import java.util.*


class AddShoppinglistActivity : AppCompatActivity() {
    private var mAdapter = ShoppinglistItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_shoppinglist)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = "Add Shoppinglist"

        val arrayList = arrayListOf<ShoppinglistItem>(ShoppinglistItem("", null, false))
        mAdapter.ShoppinglistItemAdapter(this, arrayList, R.layout.row_shoppinglist_item)
        add_shoppinglist_lists.adapter = mAdapter
        add_shoppinglist_lists.itemsCanFocus = true

        add_shoppinglist_new_item.setOnClickListener {
            showAddListItem(false)
            arrayList.add(ShoppinglistItem("", null, false))
            mAdapter.notifyDataSetChanged()
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
        mAdapter.myItems.removeAll(listOf(ShoppinglistItem("", null, null), ShoppinglistItem(null,null, null)))
//        Toast.makeText(this, mAdapter.myItems.toString(), Toast.LENGTH_SHORT).show()
        onBackPressed()
        return true
    }

    fun showAddListItem(status:Boolean){
        add_shoppinglist_new_item.visibility = when(status){
            true -> View.VISIBLE
            false -> View.GONE
        }
    }
}
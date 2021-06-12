package com.projectakhir.foodine.MainApp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projectakhir.foodine.MainApp.AddMenu.AddCalculateGoalsActivity
import com.projectakhir.foodine.MainApp.AddMenu.AddRecipeActivity
import com.projectakhir.foodine.MainApp.AddMenu.AddShoppinglistActivity
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.fragment_main_add.view.*

class MainAddFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_add, container, false)

        view.add_recipe.setOnClickListener {
            dismiss()
            val intent = Intent(activity,AddRecipeActivity::class.java)
            startActivity(intent)
        }

        view.add_shoppinglist.setOnClickListener {
            dismiss()
            val intent = Intent(activity,AddShoppinglistActivity::class.java)
            startActivity(intent)
        }

        view.add_calculate_goals.setOnClickListener {
            dismiss()
            val intent = Intent(activity,AddCalculateGoalsActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}
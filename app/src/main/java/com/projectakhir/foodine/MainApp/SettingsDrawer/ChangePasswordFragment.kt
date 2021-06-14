package com.projectakhir.foodine.MainApp.SettingsDrawer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projectakhir.foodine.AllMethod.clearEditText
import com.projectakhir.foodine.AllMethod.warningDiscardChange
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.view.*


class ChangePasswordFragment : BottomSheetDialogFragment() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)
        clearEditText(arrayListOf(view.changepass_current_txt, view.changepass_new_txt, view.changepass_confirm_txt))

        view.changepass_btn_cancel.setOnClickListener {
            if(!isContentEmpty()) warningDiscardChange(requireActivity(), (activity as SettingsAccountActivity).changePassword)
            else{
                dismiss()
            }
        }

        view.changepass_btn_submit.setOnClickListener {
            //todo : check ada yg kosong, check new+confirm sama, else kirim data
        }

        view.setOnTouchListener { v, event ->
            isContentEmpty()
            if(event.action == MotionEvent.ACTION_UP) {
                val imm: InputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            true
        }
        return view
    }

    private fun isContentEmpty() : Boolean{
        val output = changepass_current_txt.text.toString().isEmpty() &&
                changepass_new_txt.text.toString().isEmpty() &&
                changepass_confirm_txt.text.toString().isEmpty()
        isCancelable = output
        return output
    }
}
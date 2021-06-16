package com.projectakhir.foodine.MainApp.SettingsDrawer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projectakhir.foodine.AllMethod.clearEditText
import com.projectakhir.foodine.AllMethod.userData
import com.projectakhir.foodine.AllMethod.userDataDetail
import com.projectakhir.foodine.AllMethod.warningDiscardChange
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.fragment_change_email.*
import kotlinx.android.synthetic.main.fragment_change_email.view.*
import kotlinx.android.synthetic.main.fragment_change_password.view.*

class ChangeEmailFragment : BottomSheetDialogFragment() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_email, container, false)
        clearEditText(arrayListOf(view.changeemail_current_txt, view.changeemail_new_txt))
        changeemail_current_txt.setText(userData?.userEmail)

        view.changeemail_btn_cancel.setOnClickListener {
            if(!isContentEmpty()) warningDiscardChange(requireActivity(), (activity as SettingsAccountActivity).changeEmail)
            else{
                dismiss()
            }
        }

        view.changeemail_btn_submit.setOnClickListener {
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
        val output = changeemail_current_txt.text.toString().isEmpty() &&
                changeemail_new_txt.text.toString().isEmpty()
        isCancelable = output
        return output
    }
}
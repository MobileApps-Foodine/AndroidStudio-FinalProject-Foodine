package com.projectakhir.foodine.MainApp.ProfileMenu.SettingsDrawer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projectakhir.foodine.AllMethod.*
import com.projectakhir.foodine.DataClass.MainUsers
import com.projectakhir.foodine.R
import com.projectakhir.foodine.SettingAPI.Interface.UserInterface
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.ErrorHelper
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.ErrorResponse
import com.projectakhir.foodine.SettingAPI.ServerAPI
import kotlinx.android.synthetic.main.fragment_change_email.*
import kotlinx.android.synthetic.main.fragment_change_email.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeEmailFragment : BottomSheetDialogFragment() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_email, container, false)
        clearEditText(arrayListOf(view.changeemail_current_txt, view.changeemail_new_txt))
        view.changeemail_current_txt.setText(userData?.userEmail)

        val currentEmail = view.changeemail_current_txt
        val newEmail = view.changeemail_new_txt

        view.changeemail_btn_cancel.setOnClickListener {
            if(!isContentEmpty()) warningDiscardChange(requireActivity(), (activity as SettingsAccountActivity).changeEmail)
            else{
                dismiss()
            }
        }

        view.changeemail_btn_submit.setOnClickListener {
            //todo : check ada yg kosong, check new+confirm sama, else kirim data

            if (currentEmail.text.toString().isNotEmpty() && newEmail.text.toString().isNotEmpty()) {
            val serverAPI = ServerAPI()
            val userInterface : UserInterface = serverAPI.getServerAPI(requireActivity())!!.create(UserInterface::class.java)
            userInterface.userChangeEmail(hashMapOf("email" to newEmail.text.toString())).enqueue(object : Callback<MainUsers> {
                override fun onResponse(call: Call<MainUsers>, response: Response<MainUsers>) {
                    if (response.isSuccessful) {
                        userData = response.body()
                        successDialog(serverAPI.pDialog, "Change Email", "Change email is successful.")
                        dismiss()
                    } else {
                        serverAPI.pDialog.dismissWithAnimation()
                        try {
                            val output: ErrorResponse = ErrorHelper().parseErrorBody(response)
                            view.changeemail_new.error =
                                if (output.errors?.email.toString() != "null") {
                                    removeResponseRegex(output.errors?.email.toString())
                                } else {
                                    null
                                }
                        } catch (e: Exception) { }
                        }
                    }

                override fun onFailure(call: Call<MainUsers>, t: Throwable) {
                    failedDialog(serverAPI.pDialog)
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("failure", t.toString())
                }

            })
        } else {
            if (newEmail.text.toString().isEmpty()) {
                view.changeemail_new.error = " new Email can't be empty"
            }
                if (currentEmail.text.toString() == newEmail.text.toString()) {
                    view.changeemail_new.error = " new Email can't be same with current Email"
                }
        }
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
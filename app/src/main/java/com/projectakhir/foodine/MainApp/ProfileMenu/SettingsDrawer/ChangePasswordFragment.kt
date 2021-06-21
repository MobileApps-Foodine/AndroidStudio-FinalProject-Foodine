package com.projectakhir.foodine.MainApp.ProfileMenu.SettingsDrawer

import android.annotation.SuppressLint
import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_change_email.view.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.view.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChangePasswordFragment : BottomSheetDialogFragment() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)
        clearEditText(arrayListOf(view.changepass_current_txt, view.changepass_new_txt, view.changepass_confirm_txt))

        val currentPass = view.changepass_current_txt
        val newPass = view.changepass_new_txt
        val confirmNewPass = view.changepass_confirm_txt

        view.changepass_btn_cancel.setOnClickListener {
            if(!isContentEmpty()) warningDiscardChange(requireActivity(), (activity as SettingsAccountActivity).changePassword)
            else{
                dismiss()
            }
        }

        view.changepass_btn_submit.setOnClickListener {
            //todo : check ada yg kosong, check new+confirm sama, else kirim data
            val checkPassword : Boolean = newPass.text.toString() == confirmNewPass.text.toString()
            if (currentPass.text.toString().isNotEmpty() &&
                    newPass.text.toString().isNotEmpty() &&
                    confirmNewPass.text.toString().isNotEmpty() &&
                    checkPassword) {
                        val mainuser = hashMapOf("current_password" to currentPass.text.toString(),
                                                 "password" to newPass.text.toString(),
                                                "password_confirmation" to confirmNewPass.text.toString())
                val serverAPI = ServerAPI()
                val userInterface : UserInterface = serverAPI.getServerAPI(requireActivity())!!.create(UserInterface::class.java)
                userInterface.userChangePassword(mainuser).enqueue(object : Callback<MainUsers> {
                    override fun onResponse(call: Call<MainUsers>, response: Response<MainUsers>) {
                        if (response.isSuccessful) {
                            userData = response.body()
                            successDialog(serverAPI.pDialog, "Change Password", "Change Password is successful.")
                            dismiss()
                        } else {
                            serverAPI.pDialog.dismissWithAnimation()
                            try {
                                val output: ErrorResponse = ErrorHelper().parseErrorBody(response)
                                view.changepass_new.error =
                                    if (output.errors?.email.toString() != "null") {
                                        removeResponseRegex(output.errors?.email.toString())
                                    } else { null }
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
                if (newPass.text.toString() != confirmNewPass.text.toString()) {
                    view.changepass_confirm.error = "password not match"
                }
                if (currentPass.text.toString().isEmpty()){
                    view.changepass_current.error = "Current password can't be empty"
                }
                if (newPass.text.toString().isEmpty()) {
                    view.changepass_new.error = "New password can't be empty"
                }
                if (confirmNewPass.text.toString().isEmpty()) {
                    view.changepass_confirm.error = "Password Confirmation can't be empty"
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
        val output = changepass_current_txt.text.toString().isEmpty() &&
                changepass_new_txt.text.toString().isEmpty() &&
                changepass_confirm_txt.text.toString().isEmpty()
        isCancelable = output
        return output
    }
}
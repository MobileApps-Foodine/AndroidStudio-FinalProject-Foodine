package com.projectakhir.foodine.SignInUp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.projectakhir.foodine.AllMethod.*
import com.projectakhir.foodine.DataClass.MainUsers
import com.projectakhir.foodine.R
import com.projectakhir.foodine.Goals.GoalsActivity
import com.projectakhir.foodine.SettingAPI.Interface.UserInterface
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.ErrorHelper
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.ErrorResponse
import com.projectakhir.foodine.SettingAPI.ServerAPI
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        (activity as SignActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as SignActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val input_name = view.signup_input_name
        val input_email = view.signup_input_email
        val input_password = view.signup_input_password
        val input_confirm = view.signup_input_passwordConfirm
        val txt_signIn = view.signup_txtClick_signIn

        resetErrorEdittext(view.signup_layout_name, input_name)
        resetErrorEdittext(view.signup_layout_email, input_email)
        resetErrorEdittext(view.signup_layout_password, input_password)
        resetErrorEdittext(view.signup_layout_passwordConfirm, input_confirm)

        txt_signIn.setOnClickListener {
            (activity as SignActivity).onSupportNavigateUp()
        }

        view.signup_btn_signUp.setOnClickListener {
            clearFocusableAllEditText(arrayListOf(input_name, input_email, input_password, input_confirm))
            view.signup_layout_name.error = null
            view.signup_layout_email.error = null
            view.signup_layout_password.error = null
            view.signup_layout_passwordConfirm.error = null

            val checkPassword : Boolean = input_password.text.toString() == input_confirm.text.toString()
            if(input_name.text.toString().isNotEmpty() &&
                input_email.text.toString().isNotEmpty() &&
                input_password.text.toString().isNotEmpty() &&
                input_confirm.text.toString().isNotEmpty() &&
                checkPassword) {

                val mainUser = MainUsers(
                    userName = input_name.text.toString().capitalizeWords(),
                    userEmail = input_email.text.toString().lowercase(),
                    userPassword = input_password.text.toString(),
                    userPasswordConfirmation = input_confirm.text.toString())

                val serverAPI = ServerAPI()
                val userInterface : UserInterface = serverAPI.getServerAPI(requireActivity())!!.create(UserInterface::class.java)
                userInterface.userRegis(mainUser).enqueue(object : Callback<MainUsers> {
                    override fun onResponse(call: Call<MainUsers>?, response: Response<MainUsers>?) {
                        if (response!!.isSuccessful) {
                            serverAPI.pDialog.dismissWithAnimation()
                            userData = response.body()
                            userDataDetail = userData?.userDetail
                            apiToken = userData?.userAPItoken!!
                            serverAPI.pDialog.dismissWithAnimation()
                            Toast.makeText(activity, "Registration successfull", Toast.LENGTH_LONG).show()
                            val intent = Intent(activity, GoalsActivity::class.java)
                            startActivity(intent)
                        } else {
                            serverAPI.pDialog.dismissWithAnimation()
                            try {
                                val output: ErrorResponse = ErrorHelper().parseErrorBody(response)
                                view.signup_layout_email.error =
                                    if (output.errors?.email.toString() != "null") {
                                        removeResponseRegex(output.errors?.email.toString())
                                    } else {
                                        null
                                    }
                            } catch (e: Exception) { }
                        }
                    }

                    override fun onFailure(call: Call<MainUsers>?, t: Throwable) {
                        failedDialog(serverAPI.pDialog)
                        Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("failure", t.toString())
                    }
                })
            } else{
                if(input_name.text.toString().isEmpty()){
                    view.signup_layout_name.error = "Name cannot be null or empty"
                }
                if(input_email.text.toString().isEmpty()){
                    view.signup_layout_email.error = "Email cannot be null or empty"
                }
                if(input_password.text.toString().isEmpty()){
                    view.signup_layout_password.error = "Password cannot be null or empty"
                }
                if(input_confirm.text.toString().isEmpty()){
                    view.signup_layout_passwordConfirm.error = "Confirm password cannot be null or empty"
                }
                if(!checkPassword){
                    view.signup_layout_passwordConfirm.error = "Password and Confirm Password do not match"
                }
            }
        }
        return view
    }
}
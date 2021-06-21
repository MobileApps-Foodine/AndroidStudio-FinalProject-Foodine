package com.projectakhir.foodine.SignInUp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.projectakhir.foodine.AllMethod.*
import com.projectakhir.foodine.DataClass.MainUsers
import com.projectakhir.foodine.Goals.GoalsActivity
import com.projectakhir.foodine.MainApp.MainActivity
import com.projectakhir.foodine.R
import com.projectakhir.foodine.SettingAPI.Interface.UserInterface
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.ErrorHelper
import com.projectakhir.foodine.SettingAPI.ResponseDataClass.ErrorResponse
import com.projectakhir.foodine.SettingAPI.ServerAPI
import kotlinx.android.synthetic.main.activity_sign_in_up.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO : if remember me is clicked, then save user email into database. if login success, save its apiToken too
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        val input_email = view.signin_input_email
        val input_password = view.signin_input_password
        val txt_signUp = view.signin_txtClick_signUp
        val btn_signIn = view.signin_btn_signIn

        resetErrorEdittext(view.signin_layout_email, input_email)
        resetErrorEdittext(view.signin_layout_password, input_password)

        txt_signUp.setOnClickListener {
            if(activity is SignActivity){
                nav_host_fragment.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
            else{
                val intent = Intent(activity, SignActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtra("fromOnBoarding", true)
                startActivity(intent)
            }
        }

        btn_signIn.setOnClickListener {
            clearFocusableAllEditText(arrayListOf(input_email, input_password))
            if(input_email.text.toString().isNotEmpty() && input_password.text.toString().isNotEmpty()){
                val mainUser = MainUsers(
                    userEmail = input_email.text.toString(),
                    userPassword = input_password.text.toString())

                val serverAPI = ServerAPI()
                val userInterface : UserInterface = serverAPI.getServerAPI(requireActivity())!!.create(UserInterface::class.java)
                userInterface.userLogin(mainUser).enqueue(object : Callback<MainUsers> {
                    override fun onResponse(call: Call<MainUsers>?, response: Response<MainUsers>?) =
                        if (response!!.isSuccessful) {
                            userData = response.body()
                            userDataDetail = userData!!.userDetail
                            apiToken = userData?.userAPItoken!!
                            serverAPI.pDialog.dismissWithAnimation()
                            Toast.makeText(activity, "Login successfull", Toast.LENGTH_LONG).show()
                            if(userData?.userConditions != null){
                                userDataCondition = userData?.userConditions!!.size.minus(
                                    1
                                ).let { it1 -> userData!!.userConditions!!.get(it1) }
                                startActivity(Intent(activity, MainActivity::class.java))
                            }else{
                                startActivity(Intent(activity, GoalsActivity::class.java))
                            }
                        } else {
                            serverAPI.pDialog.dismissWithAnimation()
                            try {
                                val output: ErrorResponse = ErrorHelper().parseErrorBody(response)
                                view.signin_layout_email.error =
                                    if (output.errors?.email.toString() != "null") {
                                        removeResponseRegex(output.errors?.email.toString())
                                    } else {
                                        null
                                    }
                                view.signin_layout_password.error =
                                    if (output.errors?.password.toString() != "null") {
                                        removeResponseRegex(output.errors?.password.toString())
                                    } else {
                                        null
                                    }
                            } catch (e: Exception) {
                            }
                        }

                    override fun onFailure(call: Call<MainUsers>, t: Throwable) {
                        failedDialog(serverAPI.pDialog)
                        Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("failure", t.toString())
                    }
                })
            }
            else{
                if(input_email.text.toString().isEmpty()){
                    view.signin_layout_email.error = "Email cannot be null or empty"
                }
                if(input_password.text.toString().isEmpty()){
                    view.signin_layout_password.error = "Password cannot be null or empty"
                }
            }
        }

        //TODO : forget password
        return view
    }
}
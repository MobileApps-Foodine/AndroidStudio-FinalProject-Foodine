package com.projectakhir.foodine.AllMethod

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.projectakhir.foodine.R
import java.util.function.Function

fun removeResponseRegex(data : String?):String{
    return data!!.replace("[", "").replace("]","")
}

fun resetErrorEdittext(editTextInputLayout: TextInputLayout, editText: TextInputEditText){
    editText.addTextChangedListener{
        editTextInputLayout.error = null
        editTextInputLayout.focusable = View.NOT_FOCUSABLE
    }
}

fun clearFocusableAllEditText(editText: ArrayList<TextInputEditText>){
    editText.forEach { it.clearFocus() }
}

fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

fun warningDiscardChange(context:Context){
    val alertDialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
    alertDialog.setContentText("Are you sure want to discard changes made on this page?")
        .setConfirmText("Discard")
        .setConfirmClickListener {
            it.dismissWithAnimation()
            (context as Activity).onBackPressed()
        }
        .setCancelButton(
            "Cancel"
        ) { it.dismissWithAnimation() }
        .show()

    val cancelBtn = alertDialog.findViewById<Button>(R.id.cancel_button)
    cancelBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_black))
    cancelBtn.setTextColor(ContextCompat.getColor(context, R.color.prim_green))
}